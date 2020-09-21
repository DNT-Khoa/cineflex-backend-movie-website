package com.khoa.CineFlex.model;

import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ticket type name should not be blank")
    private String name;

    @NotNull(message = "Ticket type price should be identified")
    private int price;

    @Nullable
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "ticketType")
    private List<TicketDetail> ticketDetails;
}
