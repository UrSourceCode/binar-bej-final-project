package com.binar.flyket.repository;

import com.binar.flyket.model.Seat;
import com.binar.flyket.model.SeatNo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, SeatNo> {

}
