package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.BaseTest;
import com.khoa.CineFlex.model.ImageModal;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class ImageRepositoryTest extends BaseTest {
    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void shouldSaveImage() {
        ImageModal expectedImageModal = new ImageModal(null, "Image 1", "type1", "khoa@gmail.com", null);

        ImageModal actualImageModal = this.imageRepository.save(expectedImageModal);

        Assertions.assertThat(actualImageModal).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedImageModal);
    }
}
