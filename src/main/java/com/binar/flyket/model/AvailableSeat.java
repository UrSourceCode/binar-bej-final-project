package com.binar.flyket.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "available_seat")
public class AvailableSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Boolean status;

    private String row;

    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_schedule_id")
    private FlightSchedule flightSchedule;
}
