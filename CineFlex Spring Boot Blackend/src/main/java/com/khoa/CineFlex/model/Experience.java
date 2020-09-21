package com.khoa.CineFlex.model;

import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Experience name should not be blank")
    private String name;

    @NotBlank(message = "Experience description should not be blank")
    private String description;

    @NotBlank(message = "Image link should not be blank")
    private String imageLink;

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinTable(
                name = "movie_experience_link",
                joinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id")
        )
    private List<Movie> movies;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "experience")
    private List<Schedule> schedules;

}
