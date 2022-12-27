package com.binar.flyket.service;

import com.binar.flyket.dto.request.SeatRequest;
import com.binar.flyket.dummy.SeatDetailDummies;
import com.binar.flyket.repository.SeatDetailRepository;
import org.junit.jupiter.api.Assertions;
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
    private SeatDetailServiceImpl seatDetailService;

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

        var actualValue = seatDetailService.addSeat(request);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testGetAll() {

        Mockito.when(seatDetailRepository.findAll())
                .thenReturn(SeatDetailDummies.seatDetails());

        var actualValue = seatDetailService.getAll();
        var expectedSize = 3;

        Assertions.assertEquals(expectedSize, actualValue.size());
    }
}