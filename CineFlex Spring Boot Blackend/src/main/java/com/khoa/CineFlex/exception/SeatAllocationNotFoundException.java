package com.khoa.CineFlex.exception;

import com.khoa.CineFlex.model.SeatAllocation;

public class SeatAllocationNotFoundException extends RuntimeException{
    public SeatAllocationNotFoundException(String message) {
        super(message);
    }
}
