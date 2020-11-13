package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.UserDto;
import com.khoa.CineFlex.config.JwtAuthenticationEntryPoint;
import com.khoa.CineFlex.repository.UserRepository;
import com.khoa.CineFlex.service.AdminService;
import com.khoa.CineFlex.service.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminController.class)
public class AdminControllerTest {

    @MockBean
    private AdminService adminService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserRepository repository;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAdminByEmail() throws Exception{
        UserDto userDto = new UserDto("Khoa", "Doan", "khoa@gmail.com", Instant.now());
        Mockito.when(adminService.getAdminByEmail("khoa@gmail.com")).thenReturn(userDto);

        mockMvc.perform(get("/admin").param("email", "khoa@gmail.com").contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getCountAllUsers() throws Exception {
        Mockito.when(adminService.getCountAllUsers()).thenReturn(5);

        this.mockMvc.perform(get("/admin/countUsers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
