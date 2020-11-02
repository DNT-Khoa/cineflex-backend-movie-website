package com.khoa.CineFlex.model;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotBlank(message = "Backdrop image should not be blank")
    private String backdropImage;

    @NotBlank(message = "Content should not be blank")
    @Lob
    private String content;

    private Instant createdAt;

    private int views;

    @Nullable
    @ManyToMany(mappedBy = "posts", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> categories;
}
