package com.khoa.CineFlex.service;

import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.model.RefreshToken;
import com.khoa.CineFlex.repository.RefreshTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    private RefreshTokenService refreshTokenService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void setup() {
        refreshTokenService = new RefreshTokenService(refreshTokenRepository);
    }

    @Test
    void generateRefreshToken() {
        refreshTokenService.generateRefreshToken();

        Mockito.verify(refreshTokenRepository, Mockito.times(1)).save(Mockito.any(RefreshToken.class));
    }

    @Test
    void validateRefreshToken() {
        Mockito.when(refreshTokenRepository.findByToken("12345kol")).thenReturn(null);

        Assertions.assertThatThrownBy(() -> {
            refreshTokenService.validateRefreshToken("12345kol");
        }).isInstanceOf(CineFlexException.class).hasMessage("Invalid refresh token");
    }

    @Test
    void deleteRefreshToken() {
        refreshTokenService.deleteRefreshToken("12345kol");
        Mockito.verify(refreshTokenRepository, Mockito.times(1)).deleteByToken("12345kol");
    }
}
