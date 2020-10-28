package com.khoa.CineFlex.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserMovieRatingId implements Serializable {
    private Long userId;
    private Long movieId;
}
