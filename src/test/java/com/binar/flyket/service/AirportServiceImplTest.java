package com.binar.flyket.service;

import com.binar.flyket.dto.model.AirportDTO;
import com.binar.flyket.dto.model.AirportDetailDTO;
import com.binar.flyket.dto.request.InputAirportRequest;
import com.binar.flyket.dto.request.UpdateAirportRequest;
import com.binar.flyket.dummy.AirportDummies;
import com.binar.flyket.dummy.CountryDummies;
import com.binar.flyket.model.Airport;
import com.binar.flyket.repository.AirportRepository;
import com.binar.flyket.repository.CountryRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AirportServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportServiceImpl airportService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddAirport() {
        InputAirportRequest request = new InputAirportRequest();
        request.setCity("Jakarta");
        request.setIATACode("CGK");
        request.setName("Soekarno-Hatta International");
        request.setCountryCode("ID");

        Mockito.when(airportRepository.findById(request.getIATACode()))
                .thenReturn(Optional.empty());
        Mockito.when(countryRepository.findById(request.getCountryCode()))
                .thenReturn(Optional.of(CountryDummies.countryList().get(0)));

        var actualValue = airportService.addAirport(request);
        var expectedCityName = "Jakarta";
        var expectedCountryCode = "ID";

        Assertions.assertEquals(expectedCityName, actualValue.getCity());
        Assertions.assertEquals(expectedCountryCode, actualValue.getCountryCode());
    }

    @Test
    void testDeleteAirportById() {
        String IATACode = "CGK";

        Mockito.when(airportRepository.findById(IATACode))
                .thenReturn(Optional.of(AirportDummies.airports().get(0)));

        var actualValue = airportService.deleteAirportById(IATACode);
        var expectedIATACode = "CGK";
        var expectedName = "Soekarno-Hatta International";

        Assertions.assertEquals(expectedIATACode, actualValue.getIATACode());
        Assertions.assertEquals(expectedName, actualValue.getName());
    }

    @Test
    void testGetAirportById() {
        String IATACode = "CGK";

        Airport airport = AirportDummies.airports().get(0);

        AirportDetailDTO ad = new AirportDetailDTO();
        ad.setName(airport.getName());
        ad.setIATACode(airport.getIATACode());

        Mockito.when(airportRepository.findByIATACode(IATACode))
                .thenReturn(Optional.of(ad));

        var actualValue = airportService.getAirportById(IATACode);
        var expectedName = "Soekarno-Hatta International";
        var expectedIATACode = "CGK";

        Assertions.assertEquals(expectedIATACode, actualValue.getIATACode());
        Assertions.assertEquals(expectedName, actualValue.getName());
    }

    @Test
    void testGetAirports() {

        List<AirportDetailDTO> list = AirportDummies.airports().stream()
                        .map(it -> {
                            AirportDetailDTO airport = new AirportDetailDTO();
                            airport.setIATACode(it.IATACode);
                            airport.setName(it.getName());
                            return airport;
                        }).toList();

        Mockito.when(airportRepository.findAllAirport())
                .thenReturn(list);

        var actualValue = airportService.getAirports();
        var expectedSize = 2;

        Assertions.assertEquals(expectedSize, actualValue.size());
    }

    @Test
    void testUpdateAirport() {
        String IATACode = "CGK";
        UpdateAirportRequest request = new UpdateAirportRequest();
        request.setName("Hello World");
        request.setIATACode(IATACode);

        Mockito.when(airportRepository.findById(IATACode))
                .thenReturn(Optional.of(AirportDummies.airports().get(0)));

        var actualValue = airportService.updateAirport(IATACode, request);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }
}