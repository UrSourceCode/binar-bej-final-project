package com.binar.flyket.service;

import com.binar.flyket.dto.model.AircraftDTO;
import com.binar.flyket.dummy.AircraftDummies;
import com.binar.flyket.model.Aircraft;
import com.binar.flyket.repository.AircraftRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class AircraftServiceImplTest {

    @InjectMocks
    private AircraftServiceImpl aircraftService;

    @Mock
    private AircraftRepository aircraftRepository;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddAircraft() {
        AircraftDTO aircraftDTO = new AircraftDTO();
        aircraftDTO.setId(AircraftDummies.aircraftList().get(0).getId());
        aircraftDTO.setType(AircraftDummies.aircraftList().get(0).getType());

        Mockito.when(aircraftRepository.findById(aircraftDTO.getId())).thenReturn(Optional.empty());
        Mockito.when(aircraftRepository.save(AircraftDummies.aircraftList().get(0)))
                .thenReturn(AircraftDummies.aircraftList().get(0));

        var actual = aircraftService.addAircraft(aircraftDTO);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actual);
    }

    @Test
    void testUpdateAircraft() {
        AircraftDTO aircraftDTO = new AircraftDTO();
        aircraftDTO.setId(AircraftDummies.aircraftList().get(0).getId());
        aircraftDTO.setType("Boeing 777-900ER");

        Aircraft newAircraft = new Aircraft();
        newAircraft.setId(aircraftDTO.getId());
        newAircraft.setType(aircraftDTO.getType());

        Mockito.when(aircraftRepository.findById(aircraftDTO.getId())).thenReturn(Optional.of(AircraftDummies.aircraftList().get(0)));
        Mockito.when(aircraftRepository.save(newAircraft)).thenReturn(newAircraft);

        var actualValue = aircraftService.updateAircraft(aircraftDTO.getId(), aircraftDTO);
        var expectedType = "Boeing 777-900ER";

        Assertions.assertEquals(expectedType, actualValue.getType());
    }

    @Test
    void testGetById() {
        Integer aircraftId = AircraftDummies.aircraftList().get(0).getId();

        Mockito.when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(AircraftDummies.aircraftList().get(0)));

        var actualValue = aircraftService.getAircraftById(aircraftId);
        var expectedType = "Boeing 777-300ER";

        Assertions.assertEquals(expectedType, actualValue.getType());
    }


    @Test
    void testGetAircraftList() {
        Mockito.when(aircraftRepository.findAll()).thenReturn(AircraftDummies.aircraftList());
        var actualValue = aircraftService.getAircraft();
        var expectedSize = 2;
        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedSize, actualValue.size());
    }
}