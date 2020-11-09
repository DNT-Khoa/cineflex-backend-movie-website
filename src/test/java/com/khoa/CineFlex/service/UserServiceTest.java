package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.UserDto;
import com.khoa.CineFlex.mapper.MovieMapper;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.User;
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
    void getLikedMoviesOfUser() {
    }

    @Test
    void checkIfUserHasLikedMovie() {
    }

    @Test
    void likeMovie() {
    }

    @Test
    void unlikeMovie() {
    }

    @Test
    void rateMovie() {
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
