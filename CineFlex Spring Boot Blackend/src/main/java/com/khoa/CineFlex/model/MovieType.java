package com.khoa.CineFlex.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Movie Type name should not be blank")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "movieType")
    private List<Movie> movies;

    public boolean removeMovie(Movie movie) {
        return this.movies.remove(movie);
    }
}
