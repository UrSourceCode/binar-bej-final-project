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

import java.util.ArrayList;
import java.util.List;
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
    void testAddAircraftList() {
        AircraftDTO aircraftDTO1 = new AircraftDTO();
        aircraftDTO1.setId(AircraftDummies.aircraftList().get(0).getId());
        aircraftDTO1.setType(AircraftDummies.aircraftList().get(0).getType());

        AircraftDTO aircraftDTO2 = new AircraftDTO();
        aircraftDTO2.setId(AircraftDummies.aircraftList().get(1).getId());
        aircraftDTO2.setId(AircraftDummies.aircraftList().get(1).getId());

        List<AircraftDTO> listAircraft = new ArrayList<>();
        listAircraft.add(aircraftDTO1);
        listAircraft.add(aircraftDTO2);

        List<Aircraft> listModel = listAircraft.stream().map(aircraftDTO ->
        {
            Aircraft aircraftModel = new Aircraft();
            aircraftModel.setId(aircraftDTO.getId());
            aircraftModel.setType(aircraftDTO.getType());
            return aircraftModel;
        }).toList();

        Mockito.when(aircraftRepository.saveAll(listModel)).thenReturn(listModel);

        var actualVal = aircraftService.addAircraft(listAircraft);
        var expectedSize = 2;

        Assertions.assertEquals(expectedSize, actualVal.size());
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

    @Test
    void testDeleteAircraft() {
        Integer aircraftId = AircraftDummies.aircraftList().get(0).getId();

        Mockito.when(aircraftRepository.findById(aircraftId))
                .thenReturn(Optional.of(AircraftDummies.aircraftList().get(0)));
        Mockito.doNothing()
                .when(aircraftRepository).delete(AircraftDummies.aircraftList().get(0));

        var actualVal = aircraftService.deleteAircraft(aircraftId);
        var expectedId = AircraftDummies.aircraftList().get(0).getId();
        var expectedType = AircraftDummies.aircraftList().get(0).getType();

        Assertions.assertEquals(expectedId, actualVal.getId());
        Assertions.assertEquals(expectedType, actualVal.getType());
    }
}