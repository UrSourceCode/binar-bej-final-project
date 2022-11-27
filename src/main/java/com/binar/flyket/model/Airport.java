package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airport")
public class Airport {

    @Id
    public String IATACode;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "code")
    private Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "fromAirport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AirportRoute> fromRoutes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "toAirport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AirportRoute> toRoutes = new ArrayList<>();
}
