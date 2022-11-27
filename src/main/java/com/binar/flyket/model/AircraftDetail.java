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
    private Integer id;

    @Enumerated(EnumType.STRING)
    private AircraftClass aircraftClass;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "max_baggage")
    private Integer maxBaggage;

    @JsonIgnore
    @OneToMany(mappedBy = "aircraftDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Seat> seats = new ArrayList<>();


}
