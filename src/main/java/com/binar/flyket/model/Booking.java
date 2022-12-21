package com.binar.flyket.model;

import com.binar.flyket.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Column(name = "total_passenger")
    private Integer totalPassenger;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentMethod paymentMethod;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expired_time")
    private Long expiredTime;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_schedule_id")
    private FlightSchedule flightSchedule;

    @JsonIgnore
    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> ticketList = new ArrayList<>();

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
