package com.binar.flyket.service;

import com.binar.flyket.dto.request.AircraftDetailRequest;
import com.binar.flyket.dummy.AircraftDetailDummies;
import com.binar.flyket.dummy.AircraftDummies;
import com.binar.flyket.repository.AircraftDetailRepository;
import com.binar.flyket.repository.AircraftRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

class AircraftDetailServiceImplTest {


    @InjectMocks
    private AircraftDetailServiceImpl aircraftDetailService;

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AircraftDetailRepository aircraftDetailRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAircraftDetail() {
        AircraftDetailRequest request = new AircraftDetailRequest();
        request.setAircraftClass("first");
        request.setId("A001");
        request.setAircraftPrice(BigDecimal.valueOf(1_000_000));
        request.setAircraftMaxCabin(20);
        request.setAircraftMaxBaggage(7);
        request.setAircraftNo(AircraftDummies.aircraftList().get(0).getId());

        Mockito.when(aircraftDetailRepository.findById(request.getId()))
                .thenReturn(Optional.empty());
        Mockito.when(aircraftRepository.findById(request.getAircraftNo()))
                .thenReturn(Optional.of(AircraftDummies.aircraftList().get(0)));

        var actualValue = aircraftDetailService.addAircraftDetail(request);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testGetAllAircraftDetail() {
        Mockito.when(aircraftDetailRepository.findAllAircraftDetail())
                .thenReturn(AircraftDetailDummies.aircraftDetailDTOList());

        var actualValue = aircraftDetailService.getAllAircraftDetail();
        var expectedSize = 3;

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedSize, actualValue.size());
    }

    @Test
    void testDeleteById() {
        String id = "acd1";
        Mockito.when(aircraftDetailRepository.findById(id))
                .thenReturn(Optional.of(AircraftDetailDummies.getAll().get(0)));
        Mockito.doNothing()
                .when(aircraftDetailRepository).deleteById(id);

        var actualValue = aircraftDetailService.deleteById(id);
        var expectedValue = true;
        Assertions.assertEquals(expectedValue, actualValue);
    }
}