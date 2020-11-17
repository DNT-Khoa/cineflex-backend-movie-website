package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.DTO.PostResponse;
import com.khoa.CineFlex.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.title LIKE %?1%")
    List<Post> searchPostByKey(String key);

    @Query(value = "SELECT * FROM post p ORDER BY p.views DESC LIMIT 4", nativeQuery = true)
    List<Post> getFourTopNews();

    @Query("SELECT COUNT(p.id) FROM Post p")
    int getCountAllPosts();
}
