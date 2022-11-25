package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
public class Country {
    @Id
    private String code;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "countries", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Airport> airports = new ArrayList<>();
}
