package com.khoa.CineFlex.model;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "TMBD ID should be filled in")
    private Long tmdbId;

    @NotBlank(message = "Movie title should not be blank")
    private String title;

    @NotNull(message = "Rating should be filled in")
    private Double rating;

    @NotBlank(message = "Poster link should not be blank")
    private String posterLink;

    @NotBlank(message = "Backdrop link should not be blank")
    private String backdropLink;

    @NotBlank(message = "Movie Type should not be blank")
    private String movieType;

    @Nullable
    private String filmLink;

    @Nullable
    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> categories;

    @Nullable
    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE} )
    private List<User> users;

    @OneToMany(
            mappedBy = "movie",
            cascade = { CascadeType.ALL },
            orphanRemoval = true
    )
    private Set<UserMovieRating> ratedByUsers;
}
