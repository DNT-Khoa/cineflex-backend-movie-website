package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTmdbId(Long tmdbId);

    @Query("SELECT m FROM Movie m WHERE m.movieType = 'Coming Soon'")
    List<Movie> findComingSoonMovies();

    @Query("SELECT m FROM Movie m WHERE m.movieType = 'Now Playing'")
    List<Movie> findNowPlayingMovies();

    @Query(value = "SELECT * FROM movie WHERE movie_type = 'Now Playing' ORDER BY id DESC LIMIT 4", nativeQuery = true)
    List<Movie> getFourLatestNowPlayingMovie();
}
