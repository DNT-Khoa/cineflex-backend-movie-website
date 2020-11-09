package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.LikeRequest;
import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.DTO.UserDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.MovieMapper;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.Movie;
import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.model.UserMovieRating;
import com.khoa.CineFlex.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserMovieRatingRepository userMovieRatingRepository;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;
    @Mock
    private ImageRepository imageRepository;

    @BeforeEach
    public void setup() {
        userService = new UserService(userRepository, userMapper, movieRepository, movieMapper, bCryptPasswordEncoder, authenticationManager, userMovieRatingRepository, refreshTokenService, verificationTokenRepository, imageRepository);
    }

    @Test
    void getUserDetailsTest() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), null, null);
        UserDto expectedUserResponse = new UserDto("Khoa", "Doan", "khoa@gmail.com", Instant.now());

        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);
        Mockito.when(userMapper.userToDto(user)).thenReturn(expectedUserResponse);

        UserDto actualUserResponse = userService.getUserDetails("khoa@gmail.com");
        Assertions.assertThat(actualUserResponse.getFirstName()).isEqualTo(expectedUserResponse.getFirstName());
        Assertions.assertThat(actualUserResponse.getLastName()).isEqualTo(expectedUserResponse.getLastName());
    }

    @Test
    void getLikedMoviesOfUserTest() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie((long)1, (long)10, "Film 1", (double)20, "posterLink", "backdropLink", "movieType", "filmLink", null, null, null));
        movieList.add(new Movie((long)2, (long)11, "Film 2", (double)20, "posterLink", "backdropLink", "movieType", "filmLink", null, null, null));

        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), movieList, null);


        List<MovieDto> expectedMovieDtoList = new ArrayList<>();
        expectedMovieDtoList.add(new MovieDto((long)1, (long)10, "Film 1", (double) 20, "posterLink", "backdropLink", "movieType", "filmLink", null));
        expectedMovieDtoList.add(new MovieDto((long)2, (long)11, "Film 2", (double) 20, "posterLink", "backdropLink", "movieType", "filmLink", null));

        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);
        Mockito.when(movieMapper.listMovieToListDto(movieList)).thenReturn(expectedMovieDtoList);

        List<MovieDto> actualMovieDtoList = userService.getLikedMoviesOfUser("khoa@gmail.com");

        Assertions.assertThat(actualMovieDtoList.size()).isEqualTo(expectedMovieDtoList.size());
    }

    @Test
    void checkIfUserHasLikedMovieReturnTrue() {
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", null, null, null);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), movieList, null);

        LikeRequest likeRequest = new LikeRequest("khoa@gmail.com", (long)1);

        Mockito.when(movieRepository.findById((long)1)).thenReturn(java.util.Optional.of(movie));
        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        Assertions.assertThat(userService.checkIfUserHasLikedMovie(likeRequest)).isTrue();
    }

    @Test
    void checkIfUserHasLikedMovieReturnFalse() {
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", null, null, null);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null);

        LikeRequest likeRequest = new LikeRequest("khoa@gmail.com", (long)1);

        Mockito.when(movieRepository.findById((long)1)).thenReturn(java.util.Optional.of(movie));
        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        Assertions.assertThat(userService.checkIfUserHasLikedMovie(likeRequest)).isFalse();
    }

    @Test
    void checkIfUserHasLikedMovieThrowsException() {
        LikeRequest likeRequest = new LikeRequest("khoa@gmail.com", (long)1);

        Mockito.when(movieRepository.findById((long)1)).thenThrow(new CineFlexException("Cannot find user with id 1"));

        Assertions.assertThatThrownBy(() -> {
            userService.checkIfUserHasLikedMovie(likeRequest);
        }).isInstanceOf(CineFlexException.class).hasMessage("Cannot find user with id 1");
    }

    @Test
    void likeMovieTest() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null);
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", null, new ArrayList<>(), null);

        LikeRequest likeRequest = new LikeRequest("khoa@gmail.com", (long)1);

        Mockito.when(movieRepository.findById((long)1)).thenReturn(java.util.Optional.of(movie));
        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        userService.likeMovie(likeRequest);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void unlikeMovieTest() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null);
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", null, new ArrayList<>(), null);

        user.getMovies().add(movie);
        movie.getUsers().add(user);

        LikeRequest likeRequest = new LikeRequest("khoa@gmail.com", (long)1);

        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);
        Mockito.when(movieRepository.findById((long)1)).thenReturn(java.util.Optional.of(movie));

        userService.unlikeMovie(likeRequest);

        Assertions.assertThat(user.getMovies().contains(movie)).isFalse();
        Assertions.assertThat(movie.getUsers().contains(user)).isFalse();
    }

    @Test
    void rateMovie() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null);
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", null, new ArrayList<>(), null);
        UserMovieRating userMovieRating = new UserMovieRating(null, user, movie, 2);

        userMovieRatingRepository.save(userMovieRating);
        Mockito.verify(userMovieRatingRepository, Mockito.times(1)).save(userMovieRating);
    }

    @Test
    void checkIfUserHasRatedMovie() {
    }

    @Test
    void getRatingOfUserForMovie() {
    }

    @Test
    void deleteRatingRecord() {
    }

    @Test
    void editUserDetails() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void deleteAccount() {
    }
}
