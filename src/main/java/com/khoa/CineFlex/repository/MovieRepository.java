package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTmdbId(Long tmdbId);

    List<Movie> findAllByOrderByIdDesc();

    @Query(value = "SELECT * FROM movie ORDER BY id DESC LIMIT 4", nativeQuery = true)
    List<Movie> getAllMoviesLimit4();

    @Query("SELECT m FROM Movie m WHERE m.movieType = 'Coming Soon' ORDER BY m.id DESC")
    List<Movie> findComingSoonMovies();

    @Query("SELECT m FROM Movie m WHERE m.movieType = 'Now Playing' ORDER BY m.id DESC")
    List<Movie> findNowPlayingMovies();

    @Query(value = "SELECT * FROM movie WHERE movie_type = 'Now Playing' ORDER BY id DESC LIMIT 4", nativeQuery = true)
    List<Movie> getFourLatestNowPlayingMovie();

    @Query(value = "SELECT * FROM movie WHERE movie_type = 'Coming Soon' ORDER BY id DESC LIMIT 4", nativeQuery = true)
    List<Movie> getFourLatestComingSoonMovies();

    @Query(value = "SELECT * FROM movie WHERE rating > 8.0 ORDER BY rating DESC", nativeQuery = true)
    List<Movie> getTopRatedMovies();

    @Query(value = "SELECT * FROM movie WHERE rating > 8.0 ORDER BY rating DESC LIMIT 4", nativeQuery = true)
    List<Movie> getTopRatedMoviesLimit4();

    @Query("SELECT m FROM Movie m WHERE m.title LIKE %?1%")
    List<Movie> searchMovieByQueryKey(String key);

    @Query("SELECT COUNT(m.id) FROM Movie m WHERE m.movieType = 'Now Playing'")
    int getCountAllMovies();
}
