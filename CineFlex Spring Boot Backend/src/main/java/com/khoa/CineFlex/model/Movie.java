package com.khoa.CineFlex.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Director Name should not be blank")
    private String directorName;

    @NotBlank(message = "List of actors should be filled in")
    private String actorList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date addedDate;

    @NotBlank(message = "Trailer link should be added")
    private String trailerLink;

    @NotBlank(message = "Image link should not be blank")
    private String imageLink;

    @NotNull(message = "Film length should not be null")
    private int length;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieTypeId", referencedColumnName = "id")
    private MovieType movieType;

    @Nullable
    @ManyToMany(mappedBy = "movies", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MovieGenre> movieGenres;

    @Nullable
    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ViolenceLevel> violenceLevels;

    @Nullable
    @ManyToMany(mappedBy = "movies", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Experience> experiences;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "movie")
    private List<Schedule> schedules;

}
