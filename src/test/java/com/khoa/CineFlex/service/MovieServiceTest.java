package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.mapper.CategoryMapper;
import com.khoa.CineFlex.mapper.MovieMapper;
import com.khoa.CineFlex.model.Category;
import com.khoa.CineFlex.model.Movie;
import com.khoa.CineFlex.repository.CategoryRepository;
import com.khoa.CineFlex.repository.CommentRepository;
import com.khoa.CineFlex.repository.MovieRepository;
import com.khoa.CineFlex.repository.UserMovieRatingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    private MovieService movieService;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private UserMovieRatingRepository userMovieRatingRepository;
    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    public void setup() {
        movieService = new MovieService(movieRepository, movieMapper, categoryRepository, categoryMapper, userMovieRatingRepository, commentRepository);
    }

    @Test
    void getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(movieRepository.findAllByOrderByIdDesc()).thenReturn(movies);
        Mockito.when(movieMapper.listMovieToListDto(movies)).thenReturn(movieDtos);

        List<MovieDto> actualMovieDtos = movieService.getAllMovies();
        Assertions.assertThat(actualMovieDtos.size()).isEqualTo(movieDtos.size());
        Assertions.assertThat(actualMovieDtos.get(0)).isEqualTo(movieDtos.get(0));
    }

    @Test
    void getAllMoviesLimit4() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(movieRepository.getAllMoviesLimit4()).thenReturn(movies);
        Mockito.when(movieMapper.listMovieToListDto(movies)).thenReturn(movieDtos);

        List<MovieDto> actualMovieDtos = movieService.getAllMoviesLimit4();
        Assertions.assertThat(actualMovieDtos.size()).isEqualTo(movieDtos.size());
        Assertions.assertThat(actualMovieDtos.get(0)).isEqualTo(movieDtos.get(0));
    }

    @Test
    void getMovieByTmdbId() {
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        MovieDto movieDto = new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>());

        Mockito.when(movieRepository.findByTmdbId((long)1)).thenReturn(movie);
        Mockito.when(movieMapper.movieToDto(movie)).thenReturn(movieDto);

        MovieDto actualMovieDto = movieService.getMovieByTmdbId((long)1);
        Assertions.assertThat(actualMovieDto).isEqualTo(movieDto);
        Assertions.assertThat(actualMovieDto.getTmdbId()).isEqualTo(movieDto.getTmdbId());
        Assertions.assertThat(actualMovieDto.getTitle()).isEqualTo(movieDto.getTitle());

    }

    @Test
    void getAllComingMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(movieRepository.findComingSoonMovies()).thenReturn(movies);
        Mockito.when(movieMapper.listMovieToListDto(movies)).thenReturn(movieDtos);

        List<MovieDto> actualMovieDtos = movieService.getAllComingMovies();
        Assertions.assertThat(actualMovieDtos.size()).isEqualTo(movieDtos.size());
        Assertions.assertThat(actualMovieDtos.get(0)).isEqualTo(movieDtos.get(0));
    }

    @Test
    void getAllMoviesByCategory() {
        Category category = new Category((long)1, "Category name", new ArrayList<>(), new ArrayList<>());
        category.getMovies().add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(categoryRepository.findById((long)1)).thenReturn(java.util.Optional.of(category));
        Mockito.when(movieMapper.listMovieToListDto(Mockito.any())).thenReturn(movieDtos);

        List<MovieDto> actualMovieDtoList = movieService.getAllMoviesByCategory((long)1);

        Assertions.assertThat(actualMovieDtoList.size()).isEqualTo(movieDtos.size());
        Assertions.assertThat(actualMovieDtoList.get(0)).isEqualTo(movieDtos.get(0));
    }

    @Test
    void getFourLatestComingSoonMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(movieRepository.getFourLatestComingSoonMovies()).thenReturn(movies);
        Mockito.when(movieMapper.listMovieToListDto(movies)).thenReturn(movieDtos);

        List<MovieDto> actualMovieDtos = movieService.getFourLatestComingSoonMovies();
        Assertions.assertThat(actualMovieDtos.size()).isEqualTo(movieDtos.size());
        Assertions.assertThat(actualMovieDtos.get(0)).isEqualTo(movieDtos.get(0));
    }

    @Test
    void getCountMoviePerCategory() {
        Category category = new Category((long)1, "Category name", new ArrayList<>(), new ArrayList<>());
        category.getMovies().add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        Mockito.when(categoryRepository.findById((long)1)).thenReturn(java.util.Optional.of(category));

        Assertions.assertThat(movieService.getCountMoviePerCategory((long)1)).isEqualTo(1);
    }

    @Test
    void getAllNowPlayingMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(movieRepository.findNowPlayingMovies()).thenReturn(movies);
        Mockito.when(movieMapper.listMovieToListDto(movies)).thenReturn(movieDtos);

        List<MovieDto> actualMovieDtos = movieService.getAllNowPlayingMovies();
        Assertions.assertThat(actualMovieDtos.size()).isEqualTo(movieDtos.size());
        Assertions.assertThat(actualMovieDtos.get(0)).isEqualTo(movieDtos.get(0));
    }

    @Test
    void getFourLatestNowPlayingMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(movieRepository.getFourLatestNowPlayingMovie()).thenReturn(movies);
        Mockito.when(movieMapper.listMovieToListDto(movies)).thenReturn(movieDtos);

        List<MovieDto> actualMovieDtos = movieService.getFourLatestNowPlayingMovies();
        Assertions.assertThat(actualMovieDtos.size()).isEqualTo(movieDtos.size());
        Assertions.assertThat(actualMovieDtos.get(0)).isEqualTo(movieDtos.get(0));
    }

    @Test
    void getTopRatedMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(movieRepository.getTopRatedMovies()).thenReturn(movies);
        Mockito.when(movieMapper.listMovieToListDto(movies)).thenReturn(movieDtos);

        List<MovieDto> actualMovieDtos = movieService.getTopRatedMovies();
        Assertions.assertThat(actualMovieDtos.size()).isEqualTo(movieDtos.size());
        Assertions.assertThat(actualMovieDtos.get(0)).isEqualTo(movieDtos.get(0));
    }

    @Test
    void getTopRatedMoviesLimit4() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(movieRepository.getTopRatedMoviesLimit4()).thenReturn(movies);
        Mockito.when(movieMapper.listMovieToListDto(movies)).thenReturn(movieDtos);

        List<MovieDto> actualMovieDtos = movieService.getTopRatedMoviesLimit4();
        Assertions.assertThat(actualMovieDtos.size()).isEqualTo(movieDtos.size());
        Assertions.assertThat(actualMovieDtos.get(0)).isEqualTo(movieDtos.get(0));
    }

    @Test
    void createComingMovie() {
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        MovieDto movieDto = new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>());

        Mockito.when(movieMapper.movieDtoToMovie(movieDto)).thenReturn(movie);

        movieService.createComingMovie(movieDto);
        Mockito.verify(movieRepository, Mockito.times(1)).save(Mockito.any(Movie.class));
    }

    @Test
    void editMovie() {
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        MovieDto movieDto = new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>());

        Mockito.when(movieRepository.findById((long)1)).thenReturn(java.util.Optional.of(movie));

        movieService.editMovie((long)1, movieDto);
        Mockito.verify(movieRepository, Mockito.times(1)).save(Mockito.any(Movie.class));

    }

    @Test
    void deleteMovieById() {
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        Mockito.when(movieRepository.findById((long)1)).thenReturn(java.util.Optional.of(movie));

        movieService.deleteMovieById((long)1);
        Mockito.verify(movieRepository, Mockito.times(1)).deleteById((long)1);
    }

    @Test
    void checkIfMovieExitsInDatabaseReturnTrue() {
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        Mockito.when(movieRepository.findByTmdbId((long)10)).thenReturn(movie);

        Assertions.assertThat(movieService.checkIfMovieExitsInDatabase((long)10)).isTrue();
    }

    @Test
    void checkIfMovieExitsInDatabaseReturnFalse() {
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        Mockito.when(movieRepository.findByTmdbId((long)10)).thenReturn(null);

        Assertions.assertThat(movieService.checkIfMovieExitsInDatabase((long)10)).isFalse();
    }

    @Test
    void searchMovieByQueryKey() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>()));

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto((long)1, (long)10, "Movie 1", (double)10, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>()));

        Mockito.when(movieRepository.searchMovieByQueryKey("key")).thenReturn(movies);
        Mockito.when(movieMapper.listMovieToListDto(movies)).thenReturn(movieDtos);

        List<MovieDto> actualMovieDtos = movieService.searchMovieByQueryKey("key");
        Assertions.assertThat(actualMovieDtos.size()).isEqualTo(movieDtos.size());
        Assertions.assertThat(actualMovieDtos.get(0)).isEqualTo(movieDtos.get(0));
    }
}
