package com.khoa.CineFlex.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private Ticket ticket;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_allocation_id", referencedColumnName = "id")
    private SeatAllocation seatAllocation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id", referencedColumnName = "id")
    private TicketType ticketType;
}
