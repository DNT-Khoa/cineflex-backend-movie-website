package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.*;
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
import org.springframework.boot.test.mock.mockito.MockBean;
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
    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    public void setup() {
        userService = new UserService(userRepository, userMapper, movieRepository, movieMapper, bCryptPasswordEncoder, authenticationManager, userMovieRatingRepository, refreshTokenService, verificationTokenRepository, imageRepository, commentRepository);
    }

    @Test
    void getUserDetailsTest() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), null, null, null);
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

        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), movieList, null, null);


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
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), movieList, null, null);

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
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, null);

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
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, null);
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", null, new ArrayList<>(), null);

        LikeRequest likeRequest = new LikeRequest("khoa@gmail.com", (long)1);

        Mockito.when(movieRepository.findById((long)1)).thenReturn(java.util.Optional.of(movie));
        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        userService.likeMovie(likeRequest);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void unlikeMovieTest() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, null);
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
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, null);
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", null, new ArrayList<>(), null);
        UserMovieRating userMovieRating = new UserMovieRating(null, user, movie, 2);

        userMovieRatingRepository.save(userMovieRating);
        Mockito.verify(userMovieRatingRepository, Mockito.times(1)).save(userMovieRating);
    }

    @Test
    void checkIfUserHasRatedMovieReturnTrue() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, null);
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", null, new ArrayList<>(), null);

        UserMovieRating expectedUserMovieRating = new UserMovieRating(null, user, movie, 5);

        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        Mockito.when(userMovieRatingRepository.findByUserIdAndMovieId((long)1, (long)1)).thenReturn(expectedUserMovieRating);

        Assertions.assertThat(userService.checkIfUserHasRatedMovie("khoa@gmail.com", (long)1)).isTrue();
    }

    @Test
    void checkIfUserHasRatedMovieReturnFalse() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, null);
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", null, new ArrayList<>(), null);

        UserMovieRating expectedUserMovieRating = new UserMovieRating(null, user, movie, 5);

        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        Mockito.when(userMovieRatingRepository.findByUserIdAndMovieId((long)1, (long)1)).thenReturn(null);

        Assertions.assertThat(userService.checkIfUserHasRatedMovie("khoa@gmail.com", (long)1)).isFalse();
    }

    @Test
    void getRatingOfUserForMovie() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, null);
        Movie movie = new Movie((long)1, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", null, new ArrayList<>(), null);

        UserMovieRating userMovieRating = new UserMovieRating(null, user, movie, 5);

        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);
        Mockito.when(userMovieRatingRepository.findByUserIdAndMovieId((long)1, (long)1)).thenReturn(userMovieRating);

        Assertions.assertThat(userService.getRatingOfUserForMovie("khoa@gmail.com", (long)1)).isEqualTo(5);
    }

    @Test
    void deleteRatingRecord() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, null);

        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        userService.deleteRatingRecord("khoa@gmail.com", (long)1);
        Mockito.verify(userMovieRatingRepository, Mockito.times(1)).deleteByUserIdAndMovieId((long)1, (long)1);
    }

    @Test
    void editUserDetails() {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, null);
        UserEditRequest userEditRequest = new UserEditRequest("Khoa", "Doan", "khoa@gmail.com", "khoa@gmail.com", Instant.now());
        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        userService.editUserDetails(userEditRequest);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void changePassword() throws Exception {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, null);
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("khoa@gmail.com", "khoa", "khoa1");
        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        userService.changePassword(changePasswordRequest);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void deleteAccount() throws Exception {
        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), new ArrayList<>(), null, new ArrayList<>());

        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        userService.deleteAccount("khoa@gmail.com", "khoa", null);

        Mockito.verify(userRepository, Mockito.times(1)).deleteByEmail("khoa@gmail.com");
    }
}
