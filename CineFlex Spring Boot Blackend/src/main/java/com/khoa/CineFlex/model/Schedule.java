package com.khoa.CineFlex.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id", referencedColumnName = "id")
    private Cinema cinema;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id", referencedColumnName = "id")
    private Experience experience;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @NotNull(message = "Room number should be filled in")
    private int roomNumber;

    @Nullable
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schedule")
    private List<Ticket> tickets;

    @Nullable
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schedule")
    private List<SeatAllocation> seatAllocations;

}
