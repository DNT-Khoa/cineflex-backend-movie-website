package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.BaseTest;
import com.khoa.CineFlex.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class UserRepositoryTest extends BaseTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveUser() {
        User expectedUser = new User(null, "Khoa", "Doan", "khoa@gmail.com", "khoa", "User", true, Instant.now(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());

        User actualUser = this.userRepository.save(expectedUser);

        Assertions.assertThat(actualUser).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedUser);
    }
}
