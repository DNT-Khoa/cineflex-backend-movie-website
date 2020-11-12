package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.BaseTest;
import com.khoa.CineFlex.model.AdminInvitationToken;
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
public class AdminInvitationTokenRepositoryTest extends BaseTest {

    @Autowired
    private AdminInvitationTokenRepository adminInvitationTokenRepository;

    @Test
    public void shouldSaveAdminInvitationToken() {
        AdminInvitationToken expectedAdminInvitationToken = new AdminInvitationToken(null, "khoa@gmail.com", "jdsl32", Instant.now());

        AdminInvitationToken actualAdminInvitationToken = this.adminInvitationTokenRepository.save(expectedAdminInvitationToken);

        Assertions.assertThat(actualAdminInvitationToken).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedAdminInvitationToken);
    }

}
