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
@Table(name = "available_seat")
public class AvailableSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String row;

    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraftDetail_id")
    private AircraftDetail aircraftDetail;

    @JsonIgnore
    @OneToMany(mappedBy = "availableSeat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketList = new ArrayList<>();

}
