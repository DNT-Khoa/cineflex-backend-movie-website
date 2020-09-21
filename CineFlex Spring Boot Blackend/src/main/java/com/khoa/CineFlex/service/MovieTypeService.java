package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.MovieTypeDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.exception.MovieTypeNotFoundException;
import com.khoa.CineFlex.mapper.MovieTypeMapper;
import com.khoa.CineFlex.model.Movie;
import com.khoa.CineFlex.model.MovieType;
import com.khoa.CineFlex.repository.MovieTypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieTypeService {
    private final MovieTypeRepository movieTypeRepository;
    private final MovieTypeMapper movieTypeMapper;

    @Transactional
    public MovieTypeDto createMovieType(MovieTypeDto movieTypeDto) {

        MovieType movieType = movieTypeMapper.movieTypeDtoToMovieType(movieTypeDto);

        MovieType save = movieTypeRepository.save(movieType);

        return movieTypeMapper.movieTypeToDto(save);
    }

    @Transactional(readOnly = true)
    public List<MovieTypeDto> getAll() {

        return movieTypeMapper.listMovieTypeToListDto(movieTypeRepository.findAll());
    }

    @Transactional
    public void deleteMovieType(Long id) {
        MovieType movieType = movieTypeRepository.findById(id).orElseThrow(()-> new MovieTypeNotFoundException("Cannot find movie type with id " + id));

        // We need to set all the reference key from the Movie entity to MovieType to null before deleting MovieType
        for (Movie movie: movieType.getMovies()) {
            movie.setMovieType(null);
        }

        movieTypeRepository.deleteById(id);
    }
}
