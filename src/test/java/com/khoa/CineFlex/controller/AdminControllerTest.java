package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.RegisterAdminRequest;
import com.khoa.CineFlex.config.JwtAuthenticationEntryPoint;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.repository.UserRepository;
import com.khoa.CineFlex.service.AdminService;
import com.khoa.CineFlex.service.JwtUtil;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @MockBean
    private AdminService adminService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerAdmin() throws Exception {
        RegisterAdminRequest registerAdminRequest = new RegisterAdminRequest("Khoa", "Doan", "khoa", "lkdjfl32");

        Mockito.when(this.adminService.registerAdmin(registerAdminRequest)).thenReturn(true);

        this.mockMvc.perform(post("/api/registerAdmin").content(ControllerTestHelper.asJsonString(registerAdminRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
