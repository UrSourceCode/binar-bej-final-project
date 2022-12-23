package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seat_detail")
public class SeatDetail {

    @Id
    private String id;

    private Boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "seatDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketList = new ArrayList<>();

}
