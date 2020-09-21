package com.khoa.CineFlex.exception;

public class CinemaNotFoundException extends RuntimeException{
    public CinemaNotFoundException(String message) {
        super(message);
    }
}
