package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.CategoryMapper;
import com.khoa.CineFlex.mapper.MovieMapper;
import com.khoa.CineFlex.model.Category;
import com.khoa.CineFlex.model.Movie;
import com.khoa.CineFlex.repository.CategoryRepository;
import com.khoa.CineFlex.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<MovieDto> getAllMovies() {
        List<Movie> movieList = this.movieRepository.findAllByOrderByIdDesc();

        List<MovieDto> movieDtoList = this.movieMapper.listMovieToListDto(movieList);

        for (int i = 0; i < movieList.size(); i++) {
            movieDtoList.get(i).setCategories(this.categoryMapper.listCategoryToListDto(movieList.get(i).getCategories()));
        }

        return movieDtoList;
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getAllMoviesLimit4() {
        List<Movie> movieList = this.movieRepository.getAllMoviesLimit4();

        List<MovieDto> movieDtoList = this.movieMapper.listMovieToListDto(movieList);

        for (int i = 0; i < movieList.size(); i++) {
            movieDtoList.get(i).setCategories(this.categoryMapper.listCategoryToListDto(movieList.get(i).getCategories()));
        }

        return movieDtoList;
    }

    @Transactional(readOnly = true)
    public MovieDto getMovieByTmdbId(Long tmdbId) {
        Movie movie = this.movieRepository.findByTmdbId(tmdbId);

        MovieDto movieDto = this.movieMapper.movieToDto(movie);

        movieDto.setCategories(this.categoryMapper.listCategoryToListDto(movie.getCategories()));

        return movieDto;
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getAllComingMovies() {
        List<Movie> movieList = this.movieRepository.findComingSoonMovies();

        List<MovieDto> movieDtoList = this.movieMapper.listMovieToListDto(movieList);

        for (int i = 0; i < movieList.size(); i++) {
            movieDtoList.get(i).setCategories(this.categoryMapper.listCategoryToListDto(movieList.get(i).getCategories()));
        }

        return movieDtoList;
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getFourLatestComingSoonMovies() {
        List<Movie> movieList = this.movieRepository.getFourLatestComingSoonMovies();

        List<MovieDto> movieDtoList = this.movieMapper.listMovieToListDto(movieList);

        for (int i = 0; i < movieList.size(); i++) {
            movieDtoList.get(i).setCategories(this.categoryMapper.listCategoryToListDto(movieList.get(i).getCategories()));
        }

        return movieDtoList;
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getAllNowPlayingMovies() {
        List<Movie> movieList = this.movieRepository.findNowPlayingMovies();

        List<MovieDto> movieDtoList = this.movieMapper.listMovieToListDto(movieList);

        for (int i = 0; i < movieList.size(); i++) {
            movieDtoList.get(i).setCategories(this.categoryMapper.listCategoryToListDto(movieList.get(i).getCategories()));
        }

        return movieDtoList;
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getFourLatestNowPlayingMovies() {
        List<Movie> movieList = this.movieRepository.getFourLatestNowPlayingMovie();

        List<MovieDto> movieDtoList = this.movieMapper.listMovieToListDto(movieList);

        for (int i = 0; i < movieList.size(); i++) {
            movieDtoList.get(i).setCategories(this.categoryMapper.listCategoryToListDto(movieList.get(i).getCategories()));
        }

        return movieDtoList;
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getTopRatedMovies() {
        List<Movie> movieList = this.movieRepository.getTopRatedMovies();

        List<MovieDto> movieDtoList = this.movieMapper.listMovieToListDto(movieList);

        for (int i = 0; i < movieList.size(); i++) {
            movieDtoList.get(i).setCategories(this.categoryMapper.listCategoryToListDto(movieList.get(i).getCategories()));
        }

        return movieDtoList;
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getTopRatedMoviesLimit4() {
        List<Movie> movieList = this.movieRepository.getTopRatedMoviesLimit4();

        List<MovieDto> movieDtoList = this.movieMapper.listMovieToListDto(movieList);

        for (int i = 0; i < movieList.size(); i++) {
            movieDtoList.get(i).setCategories(this.categoryMapper.listCategoryToListDto(movieList.get(i).getCategories()));
        }

        return movieDtoList;
    }

    @Transactional
    public MovieDto createComingMovie(MovieDto movieDto) {
        Movie check = this.movieRepository.findByTmdbId(movieDto.getTmdbId());
        if (check != null) {
            throw new CineFlexException("Movie already exits!");
        }

        Movie movie = this.movieMapper.movieDtoToMovie(movieDto);

        List<Category> categoryList = new ArrayList<>();
        for (CategoryDto categoryDto : movieDto.getCategories()) {
            Category category = this.categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new CineFlexException("Cannot find category with id: " + categoryDto.getId()));
            category.getMovies().add(movie);
            categoryList.add(category);
        }
        movie.setCategories(categoryList);

        Movie save = this.movieRepository.save(movie);

        return this.movieMapper.movieToDto(save);
    }

    @Transactional
    public MovieDto editMovie(Long movieId, MovieDto movieDto) {
        Movie movieToBeUpdate = this.movieRepository.findById(movieId).orElseThrow(() -> new CineFlexException("Cannot find movie with id: " + movieId));

        for (Category category : movieToBeUpdate.getCategories()) {
            category.getMovies().remove(movieToBeUpdate);
        }

        movieToBeUpdate.getCategories().clear();

        List<Category> categoryList = new ArrayList<>();
        for (CategoryDto categoryDto: movieDto.getCategories()) {
            Category category = this.categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new CineFlexException("Cannot find category with id: " + categoryDto.getId()));
            category.getMovies().add(movieToBeUpdate);
            categoryList.add(category);
        }
        movieToBeUpdate.setCategories(categoryList);

        movieToBeUpdate.setTmdbId(movieDto.getTmdbId());
        movieToBeUpdate.setTitle(movieDto.getTitle());
        movieToBeUpdate.setRating(movieDto.getRating());
        movieToBeUpdate.setPosterLink(movieDto.getPosterLink());
        movieToBeUpdate.setBackdropLink(movieDto.getBackdropLink());
        movieToBeUpdate.setMovieType(movieDto.getMovieType());
        movieToBeUpdate.setFilmLink(movieDto.getFilmLink());

        Movie save = this.movieRepository.save(movieToBeUpdate);

        return this.movieMapper.movieToDto(save);
    }

    @Transactional
    public void deleteMovieById(Long movieId) {
        Movie movieToBeDeleted = this.movieRepository.findById(movieId).orElseThrow(() -> new CineFlexException("Cannot find movie with id: " + movieId));

        for (Category category : movieToBeDeleted.getCategories()) {
            category.getMovies().remove(movieToBeDeleted);
        }

        this.movieRepository.deleteById(movieId);
    }

    @Transactional
    public List<MovieDto> searchMovieByQueryKey(String key) {
        List<Movie> movieList = this.movieRepository.searchMovieByQueryKey(key);

        List<MovieDto> movieDtoList = this.movieMapper.listMovieToListDto(movieList);

        for (int i = 0; i < movieList.size(); i++) {
            movieDtoList.get(i).setCategories(this.categoryMapper.listCategoryToListDto(movieList.get(i).getCategories()));
        }

        return movieDtoList;
    }
}
