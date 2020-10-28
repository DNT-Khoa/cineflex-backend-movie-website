package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.*;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.MovieMapper;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.Movie;
import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.model.UserMovieRating;
import com.khoa.CineFlex.repository.MovieRepository;
import com.khoa.CineFlex.repository.UserMovieRatingRepository;
import com.khoa.CineFlex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMovieRatingRepository userMovieRatingRepository;


    @Transactional(readOnly = true)
    public UserDto getUserDetails(String userEmail) {
        UserDto user = this.userMapper.userToDto(this.userRepository.findByEmail(userEmail));
        return user;
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getLikedMoviesOfUser(String userEmail) {
        User user = this.userRepository.findByEmail(userEmail);
        return this.movieMapper.listMovieToListDto(user.getMovies());
    }

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

    public boolean rateMovie(RatingDto ratingDto) {
        User user = userRepository.findByEmail(ratingDto.getUserEmail());
        Movie movie = movieRepository.findById(ratingDto.getMovieId()).orElseThrow(() -> new CineFlexException("Cannot find movie with id: " + ratingDto.getMovieId()));
        int rating = ratingDto.getRating();

        UserMovieRating userMovieRating = new UserMovieRating();
        userMovieRating.setMovie(movie);
        userMovieRating.setUser(user);
        userMovieRating.setRating(rating);

        this.userMovieRatingRepository.save(userMovieRating);

        return true;
    }

    @Transactional
    public UserDto editUserDetails(UserEditRequest userEditRequest) {
        if (!userEditRequest.getOldEmail().equals(userEditRequest.getNewEmail())) {
            User checkUser = this.userRepository.findByEmail(userEditRequest.getNewEmail());

            if (checkUser != null) {
                throw new CineFlexException("Email already exits in the database");
            }
        }

        User user = this.userRepository.findByEmail(userEditRequest.getOldEmail());
        user.setFirstName(userEditRequest.getFirstName());
        user.setLastName(userEditRequest.getLastName());
        user.setEmail(userEditRequest.getNewEmail());

        return this.userMapper.userToDto(this.userRepository.save(user));
    }

    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword()));
        } catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        User user = this.userRepository.findByEmail(changePasswordRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));

        this.userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        User user = this.userRepository.findByEmail(email);

        // Delete all user references from the User Movie Like table
        for (Movie movie : user.getMovies()) {
            movie.getUsers().remove(user);
        }

        // Delete the user references in the User Movie Rating table
        this.userMovieRatingRepository.deleteByUserEmail(email);

        this.userRepository.deleteByEmail(email);
    }
}
