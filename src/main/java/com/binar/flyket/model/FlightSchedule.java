package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "flight_schedule")
public class FlightSchedule {

    @Id
    private String id;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private LocalDate flightDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "aircraft_detail_id")
    private AircraftDetail aircraftDetail;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_route_id")
    private FlightRoute flightRoute;
}
