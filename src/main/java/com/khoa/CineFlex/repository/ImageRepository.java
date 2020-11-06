package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.ImageModal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageModal, Long> {
    ImageModal findByEmail(String email);
}
