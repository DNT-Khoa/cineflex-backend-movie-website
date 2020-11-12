package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.BaseTest;
import com.khoa.CineFlex.model.RefreshToken;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class RefreshTokenRepositoryTest extends BaseTest {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    public void shouldSaveRefreshToken() {
        RefreshToken expectedRefreshToken = new RefreshToken(null, "dlks235", Instant.now());

        RefreshToken actualRefreshToken = this.refreshTokenRepository.save(expectedRefreshToken);

        Assertions.assertThat(actualRefreshToken).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedRefreshToken);
    }
}
