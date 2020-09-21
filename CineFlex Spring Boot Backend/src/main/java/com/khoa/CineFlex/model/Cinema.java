package com.khoa.CineFlex.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Cinema name should not be blank")
    private String name;

    @Lob
    @NotBlank(message = "Cinema description should not be blank")
    private String description;

    @NotBlank(message = "Cinema address should not be blank")
    private String address;

    @NotBlank(message = "Cinema image link should be filled in")
    private String imageLink;

    @NotBlank(message = "Cinema accessibility section should not be blank")
    @Lob
    private String accessibility;

    @NotBlank(message = "Opening hours should not be blank")
    @Lob
    private String openingHours;

    @NotBlank(message = "General information should not be blank")
    @Lob
    private String generalInformation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cinema")
    private List<Schedule> schedules;
}

