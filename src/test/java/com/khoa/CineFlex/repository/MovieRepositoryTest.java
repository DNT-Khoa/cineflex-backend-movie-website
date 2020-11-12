package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.BaseTest;
import com.khoa.CineFlex.model.Movie;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class MovieRepositoryTest extends BaseTest {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void shouldSaveMovie() {
        Movie expectedMovie = new Movie(null, (long)10, "Movie 1", (double)5, "posterLink", "backdropLink", "movieType", "filmLink", new ArrayList<>(), new ArrayList<>(), new HashSet<>());

        Movie actualMovie = this.movieRepository.save(expectedMovie);

        Assertions.assertThat(actualMovie).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedMovie);
    }
}
