package com.khoa.CineFlex.service;

import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;


@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImpTest {
    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        UserDetailsServiceImp userDetailsServiceImp = new UserDetailsServiceImp(userRepository);

        User user = new User((long) 1, "Khoa", "Doan", "khoa@gmail.com", "12345", "User", true, Instant.now(), null, null);
        Mockito.when(userRepository.findByEmail("khoa@gmail.com")).thenReturn(user);

        org.springframework.security.core.userdetails.User actualUser = (org.springframework.security.core.userdetails.User) userDetailsServiceImp.loadUserByUsername("khoa@gmail.com");

        Assertions.assertThat(actualUser.getUsername()).isEqualTo(user.getEmail());
    }
}
