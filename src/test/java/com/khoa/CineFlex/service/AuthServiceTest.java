package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.LoginRequest;
import com.khoa.CineFlex.DTO.RegisterRequest;
import com.khoa.CineFlex.config.AppConfig;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.repository.UserRepository;
import com.khoa.CineFlex.repository.VerificationTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private UserMapper userMapper;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private Environment environment;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private MailService mailService;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;
    @Mock
    private AppConfig appConfig;

    @BeforeEach
    public void setup() {
        authService = new AuthService(userRepository, bCryptPasswordEncoder, userMapper, authenticationManager, userDetailsService, jwtUtil, environment, refreshTokenService, mailService, verificationTokenRepository, appConfig);
    }

    @Test
    void signupReturnFalse() {
        RegisterRequest registerRequest = new RegisterRequest("Khoa", "Doan", "khoa@gmail.com", "khoa");

        User user = new User((long)1, "Khoa", "Doan", "khoa@gmail.com", "khoa", "User", true, Instant.now(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());

        Mockito.when(userMapper.registerRequestToUser(registerRequest)).thenReturn(user);
        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        Assertions.assertThat(authService.signup(registerRequest, "User")).isEqualTo(false);
    }

    @Test
    void signupReturnTrue() {
        RegisterRequest registerRequest = new RegisterRequest("Khoa", "Doan", "khoa@gmail.com", "khoa");

        User user = new User((long)1, "Khoa", "Doan", "khoa@gmail.com", "khoa", "User", true, Instant.now(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());

        Mockito.when(userMapper.registerRequestToUser(registerRequest)).thenReturn(user);
        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(null);

        Boolean result = authService.signup(registerRequest, "User");

        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    void loginThrowCineFlexException() {
        LoginRequest loginRequest = new LoginRequest("khoa@gmail.com", "khoa");
        User user = new User((long)1, "Khoa", "Doan", "khoa@gmail.com", "khoa", "User", true, Instant.now(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());

        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(null);

        Assertions.assertThatThrownBy(() -> {
            authService.login(loginRequest);
        }).isInstanceOf(CineFlexException.class).hasMessage("EMAIL_NOT_EXISTS");
    }
}
