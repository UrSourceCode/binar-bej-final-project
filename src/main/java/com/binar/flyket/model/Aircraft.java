package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "aircraft")
public class Aircraft {

    @Id
    private Integer id;

    @Column(name = "type")
    private String type;

    @JsonIgnore
    @OneToMany(mappedBy = "aircraft", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<AircraftDetail> aircraftDetailList = new ArrayList<>();
}
