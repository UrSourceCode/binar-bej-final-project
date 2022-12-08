package com.binar.flyket.repository;

import com.binar.flyket.model.AvailableSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableSeatRepository extends JpaRepository<AvailableSeat, Integer> {
}
