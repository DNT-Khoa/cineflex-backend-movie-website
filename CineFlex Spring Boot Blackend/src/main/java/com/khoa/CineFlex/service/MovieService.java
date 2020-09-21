package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.exception.*;
import com.khoa.CineFlex.mapper.MovieMapper;
import com.khoa.CineFlex.model.*;
import com.khoa.CineFlex.repository.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieTypeRepository movieTypeRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final ViolenceLevelRepository violenceLevelRepository;
    private final ExperienceRepository experienceRepository;
    private final MovieMapper movieMapper;

    @Transactional
    public MovieResponse createMovie(MovieDto movieDto) {

        Movie movie = movieMapper.movieDtoToMovie(movieDto);
        movie.setAddedDate(new Date());

        MovieType movieType = movieTypeRepository.findById(movieDto.getMovieTypeId()).orElseThrow(() -> new MovieTypeNotFoundException("Cannot find movie type with id " + movieDto.getMovieTypeId()));
        movie.setMovieType(movieType);
        movieType.getMovies().add(movie);

        // Add all movie genres to the movie and vice versa
        List<MovieGenre> movieGenreList = new ArrayList<>();
        for (Long movieGenreId: movieDto.getMovieGenreIdList()) {
            MovieGenre movieGenre = movieGenreRepository.findById(movieGenreId).orElseThrow(() -> new MovieGenreNotFoundException("Cannot find movie genre with id " + movieGenreId));
            movieGenreList.add(movieGenre);

            movieGenre.getMovies().add(movie);
        }
        movie.setMovieGenres(movieGenreList);

        // Add all violence level to the movie and vice versa
        List<ViolenceLevel> violenceLevelList = new ArrayList<>();
        for (Long violenceLevelId: movieDto.getViolenceLevelIdList()) {
            ViolenceLevel violenceLevel = violenceLevelRepository.findById(violenceLevelId).orElseThrow(() -> new ViolenceLevelNotFoundException("Cannot find violence level with id " + violenceLevelId));
            violenceLevelList.add(violenceLevel);
            violenceLevel.getMovies().add(movie);
        }
        movie.setViolenceLevels(violenceLevelList);

        // Add all experiences to the movie and vice versa
        List<Experience> experienceList = new ArrayList<>();
        for (Long experienceId: movieDto.getExperienceIdList()) {
            Experience experience = experienceRepository.findById(experienceId).orElseThrow(()->new ExperienceNotFoundException("Cannot find experience with id " + experienceId));
            experienceList.add(experience);
            experience.getMovies().add(movie);
        }
        movie.setExperiences(experienceList);

        Movie save = movieRepository.save(movie);


        return movieMapper.movieToMovieResponse(save);
    }

    @Transactional(readOnly = true)
    public MovieResponse getMovieById(Long movieId) {
        return movieMapper.movieToMovieResponse(movieRepository.findById(movieId).orElseThrow(()-> new MovieNotFoundException("Cannot find movie with id " + movieId)));
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getMovieByMovieType(Long movieTypeId) {
        MovieType movieType = movieTypeRepository.findById(movieTypeId).orElseThrow(() -> new MovieTypeNotFoundException("Cannot find movie type with id " + movieTypeId));

        return movieMapper.movieListToListDto(movieType.getMovies());
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getMovieByMovieGenre(Long movieGenreId) {
        MovieGenre movieGenre = movieGenreRepository.findById(movieGenreId).orElseThrow(() -> new MovieGenreNotFoundException("Cannot find movie genre with id" + movieGenreId));

        return movieMapper.movieListToListDto(movieGenre.getMovies());
    }

    @Transactional
    public void updateMovieById(Long movieId, MovieDto movieDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Cannot find movie with id " + movieId));
        movie.setTitle(movieDto.getTitle());
        movie.setDirectorName(movieDto.getDirectorName());
        movie.setActorList(movieDto.getActorList());
        movie.setTrailerLink(movieDto.getTrailerLink());
        movie.setImageLink(movieDto.getImageLink());
        movie.setLength(movieDto.getLength());

        MovieType newMovieType = movieTypeRepository.findById(movieDto.getMovieTypeId()).orElseThrow(()-> new MovieTypeNotFoundException("Cannot find movie type with id " + movieDto.getMovieTypeId()));
        MovieType oldMovieType = movieTypeRepository.findById(movieDto.getMovieTypeId()).orElseThrow(()-> new MovieTypeNotFoundException("cannot find movie type"));
        oldMovieType.getMovies().remove(movie);
        movie.setMovieType(newMovieType);
        newMovieType.getMovies().add(movie);

        // Remove old movie genres and add new movie genres
        for (MovieGenre movieGenre: movie.getMovieGenres()) {
            movieGenre.getMovies().remove(movie);
        }

        List<MovieGenre> movieGenreList = new ArrayList<>();
        for (Long movieGenreId: movieDto.getMovieGenreIdList()) {
            MovieGenre movieGenre = movieGenreRepository.findById(movieGenreId).orElseThrow(() -> new MovieGenreNotFoundException("Cannot find movie genre with id " + movieGenreId));
            movieGenreList.add(movieGenre);

            movieGenre.getMovies().add(movie);
        }
        movie.setMovieGenres(movieGenreList);

        // Remove old violence levels and add new violence levels
        for (ViolenceLevel violenceLevel: movie.getViolenceLevels()) {
            violenceLevel.getMovies().remove(movie);
        }

        List<ViolenceLevel> violenceLevelList = new ArrayList<>();
        for (Long violenceLevelId: movieDto.getViolenceLevelIdList()) {
            ViolenceLevel violenceLevel = violenceLevelRepository.findById(violenceLevelId).orElseThrow(() -> new ViolenceLevelNotFoundException("Cannot find violence level with id " + violenceLevelId));
            violenceLevelList.add(violenceLevel);
            violenceLevel.getMovies().add(movie);
        }
        movie.setViolenceLevels(violenceLevelList);

        // Remove old experiences and add new experiences
        for (Experience experience: movie.getExperiences()) {
            experience.getMovies().remove(movie);
        }

        List<Experience> experienceList = new ArrayList<>();
        for (Long experienceId: movieDto.getExperienceIdList()) {
            Experience experience = experienceRepository.findById(experienceId).orElseThrow(()->new ExperienceNotFoundException("Cannot find experience with id " + experienceId));
            experienceList.add(experience);
            experience.getMovies().add(movie);
        }
        movie.setExperiences(experienceList);

        movieRepository.save(movie);
    }

    // Because Movie is not the owning side of many to many relationship so
    // you can only delete it by delete it from the owning side
    @Transactional
    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() ->new CineFlexException("Cannot find the movie with id " + movieId));
        // Delete all movies from the movie genre side
        for (MovieGenre movieGenre: movie.getMovieGenres()) {
            movieGenre.getMovies().remove(movie);
        }

        // Delete all movies from the violence level side
        for (ViolenceLevel violenceLevel: movie.getViolenceLevels()) {
            violenceLevel.getMovies().remove(movie);
        }

        // Delete all movies from the experiences side
        for (Experience experience: movie.getExperiences()) {
            experience.getMovies().remove(movie);
        }

        // Delete the movie from the database
        movieRepository.deleteById(movieId);
    }
}
