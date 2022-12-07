package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "seat_detail")
public class SeatDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    private Boolean status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seatDetail")
    private List<Ticket> ticket = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_schedule")
    private FlightSchedule schedule;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "seat_no"),
            @JoinColumn(name = "seat_row"),
    })
    private Seat seat;
}
