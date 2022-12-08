package com.binar.flyket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    private String id;

    private String passengerName;
    private String passengerTitle;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_schedule_id")
    private FlightSchedule flightSchedule;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "available_seat_id")
    private AvailableSeat availableSeat;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
