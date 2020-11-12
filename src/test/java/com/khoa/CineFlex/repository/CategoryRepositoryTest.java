package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.BaseTest;
import com.khoa.CineFlex.model.Category;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class CategoryRepositoryTest extends BaseTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void shouldSaveCategory() {
        Category expectedCategory = new Category(null, "Category 1", new ArrayList<>(), new ArrayList<>());

        Category actualCategory = this.categoryRepository.save(expectedCategory);

        Assertions.assertThat(actualCategory).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedCategory);
    }
}
