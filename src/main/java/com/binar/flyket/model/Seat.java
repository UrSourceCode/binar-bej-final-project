package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "seat")
public class Seat {

    @EmbeddedId
    private SeatNo id;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @JsonIgnore
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<SeatDetail> seatDetails = new ArrayList<>();

    @JsonIgnore
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDate createdAt;

    @JsonIgnore
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDate updatedAt;
}
