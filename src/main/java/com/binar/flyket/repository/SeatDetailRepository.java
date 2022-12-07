package com.binar.flyket.repository;

import com.binar.flyket.model.SeatDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface SeatDetailRepository extends JpaRepository<SeatDetail, BigInteger> { }
