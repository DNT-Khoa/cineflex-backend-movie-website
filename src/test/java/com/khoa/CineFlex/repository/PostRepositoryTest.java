package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.BaseTest;
import com.khoa.CineFlex.model.Post;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class PostRepositoryTest extends BaseTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void shouldSavePost() {
        Post expectedPost = new Post(null, "Post 1", "backdropImage", "content", Instant.now(), 100, new ArrayList<>());

        Post actualPost = this.postRepository.save(expectedPost);

        Assertions.assertThat(actualPost).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedPost);
    }
}
