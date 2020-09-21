package com.khoa.CineFlex.exception;

public class MovieGenreNotFoundException extends RuntimeException{
    public MovieGenreNotFoundException(String message) {
        super(message);
    }
}
