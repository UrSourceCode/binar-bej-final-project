package com.binar.flyket.service;

import com.binar.flyket.dto.request.SeatRequest;
import com.binar.flyket.repository.SeatDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SeatDetailServiceImplTest {

    @InjectMocks
    private SeatDetailService seatDetailService;

    @Mock
    private SeatDetailRepository seatDetailRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddSeat() {
        SeatRequest request = new SeatRequest();
        request.setSeatNo("1A");

        Mockito.when(seatDetailRepository.findById(request.getSeatNo()))
                .thenReturn(Optional.empty());

    }

    @Test
    void testGetAll() {

    }
}