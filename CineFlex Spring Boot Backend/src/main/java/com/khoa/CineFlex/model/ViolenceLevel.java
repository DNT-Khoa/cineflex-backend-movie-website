package com.khoa.CineFlex.model;

import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViolenceLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Violence Level Name should not be blank")
    private String name;

    @NotBlank(message = "Image link for Violence Level should not be blank")
    private String imageLink;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
        @JoinTable(
                name = "movie_violence_level_link",
                joinColumns = @JoinColumn(name = "violence_level_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id")
        )
    private List<Movie> movies;
}
