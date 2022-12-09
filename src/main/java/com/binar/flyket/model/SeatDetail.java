package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "seat_detail")
public class SeatDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String row;

    private Integer no;

    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraftDetail_id")
    private AircraftDetail aircraftDetail;

    @JsonIgnore
    @OneToMany(mappedBy = "seatDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketList = new ArrayList<>();

}
