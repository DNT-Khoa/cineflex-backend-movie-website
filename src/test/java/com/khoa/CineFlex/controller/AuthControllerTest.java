package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.*;
import com.khoa.CineFlex.config.JwtAuthenticationEntryPoint;
import com.khoa.CineFlex.repository.UserRepository;
import com.khoa.CineFlex.service.AuthService;
import com.khoa.CineFlex.service.JwtUtil;
import com.khoa.CineFlex.service.RefreshTokenService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private RefreshTokenService refreshTokenService;


    @Test
    public void signupReturnTrue() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("Khoa", "Doan", "khoa@gmail.com", "khoa");

        Mockito.when(authService.signup(registerRequest, "User")).thenReturn(true);

        this.mockMvc.perform(post("/api/auth/signup").content(ControllerTestHelper.asJsonString(registerRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(Matchers.containsString("User Registration Successful")));
    }

    @Test
    public void signupReturnFalse() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("Khoa", "Doan", "khoa@gmail.com", "khoa");

        Mockito.when(authService.signup(registerRequest, "User")).thenReturn(false);

        this.mockMvc.perform(post("/api/auth/signup").content(ControllerTestHelper.asJsonString(registerRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string(Matchers.containsString("Email already exists!")));
    }

    @Test
    public void login() throws Exception {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("dslkj32", "lkjdfl34", Instant.now(), "khoa@gmail.com", "User");
        LoginRequest loginRequest = new LoginRequest("khoa@gmail.com", "khoa");

        Mockito.when(authService.login(loginRequest)).thenReturn(authenticationResponse);

        this.mockMvc.perform(post("/api/auth/login").content(ControllerTestHelper.asJsonString(loginRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.authenticationToken").value("dslkj32"))
                .andExpect(jsonPath("$.refreshToken").value("lkjdfl34"))
                .andExpect(jsonPath("$.email").value("khoa@gmail.com"));
    }


    @Test
    public void logout() throws Exception {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("dkljlk34", "khoa@gmail.com", "User");

        this.mockMvc.perform(post("/api/auth/logout").content(ControllerTestHelper.asJsonString(refreshTokenRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void forgotPassword() throws Exception {
        this.mockMvc.perform(post("/api/auth/forgotPassword").param("email", "khoa@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void resetPassword() throws Exception {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("kj3lk5", "khoa");

        this.mockMvc.perform(post("/api/auth/resetPassword").content(ControllerTestHelper.asJsonString(resetPasswordRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
