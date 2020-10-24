package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.LikeRequest;
import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.DTO.UserDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.Movie;
import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.repository.MovieRepository;
import com.khoa.CineFlex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public boolean checkIfUserHasLikedMovie(LikeRequest likeRequest) {
        Movie movie = movieRepository.findById(likeRequest.getMovieId()).orElseThrow(() -> new CineFlexException("Cannot find movie with id: " + likeRequest.getMovieId()));
        User user = userRepository.findByEmail(likeRequest.getEmail());

        if (user.getMovies().contains(movie)) {
            return true;
        }

        return false;
    }

    @Transactional
    public UserDto likeMovie(LikeRequest likeRequest) {
        Movie movie = movieRepository.findById(likeRequest.getMovieId()).orElseThrow(() -> new CineFlexException("Cannot find movie with id: " + likeRequest.getMovieId()));
        User user = userRepository.findByEmail(likeRequest.getEmail());

        user.getMovies().add(movie);
        movie.getUsers().add(user);

        return this.userMapper.userToDto(this.userRepository.save(user));
    }

    @Transactional
    public void unlikeMovie(LikeRequest likeRequest) {
        Movie movie = movieRepository.findById(likeRequest.getMovieId()).orElseThrow(() -> new CineFlexException("Cannot find movie with id: " + likeRequest.getMovieId()));
        User user = userRepository.findByEmail(likeRequest.getEmail());

        user.getMovies().remove(movie);
        movie.getUsers().remove(user);
    }
}
