package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "aircraft_detail")
public class AircraftDetail {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private AircraftClass aircraftClass;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "max_baggage")
    private Integer maxBaggage;

    @Column(name = "max_cabin")
    private Integer maxCabin;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @JsonIgnore
    @OneToMany(mappedBy = "aircraftDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FlightSchedule> flightSchedules = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "aircraftDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SeatDetail> seatDetailList = new ArrayList<>();

}
