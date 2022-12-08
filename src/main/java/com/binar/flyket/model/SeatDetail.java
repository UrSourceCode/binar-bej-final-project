package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "seat_detail")
public class SeatDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Boolean status;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "seat_no",  nullable = false, updatable = false, insertable = false),
            @JoinColumn(name = "seat_row",  nullable = false, updatable = false, insertable = false),
    })
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "aircraft_id", nullable = false, updatable = false, insertable = false)
    private AircraftDetail aircraftDetail;
}
