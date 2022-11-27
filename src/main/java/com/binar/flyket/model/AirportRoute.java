package com.binar.flyket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "airport_route")
public class AirportRoute {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_from_airport", referencedColumnName = "IATACode")
    private Airport fromAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_to_airport", referencedColumnName = "IATACode")
    private Airport toAirport;

    @Column(name = "hours")
    private Integer hours;

    @Column(name = "minutes")
    private Integer minutes;

    @JsonIgnore
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
