package com.khoa.CineFlex.exception;

public class MovieTypeNotFoundException extends RuntimeException{
    public MovieTypeNotFoundException(String message) {
        super(message);
    }
}
