package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.BaseTest;
import com.khoa.CineFlex.model.VerificationToken;
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
public class VerificationTokenRepositoryTest extends BaseTest {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Test
    public void shouldSaveVerificationToken() {
        VerificationToken expectedVerificationToken = new VerificationToken(null, "kdsjllk32", "khoa@gmail.com", Instant.now());

        VerificationToken actualVerificationToken = this.verificationTokenRepository.save(expectedVerificationToken);

        Assertions.assertThat(actualVerificationToken).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedVerificationToken);
    }
}
