package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightRouteDetailDTO;
import com.binar.flyket.dto.request.AirportRouteRequest;
import com.binar.flyket.dto.request.UpdateRouteRequest;
import com.binar.flyket.dummy.AirportDummies;
import com.binar.flyket.dummy.FlightRouteDummies;
import com.binar.flyket.repository.AirportRepository;
import com.binar.flyket.repository.AirportRouteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class FlightRouteServiceImplTest {

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private AirportRouteRepository airportRouteRepository;

    @InjectMocks
    private FlightRouteServiceImpl flightRouteService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddAirportRoute() {
        AirportRouteRequest request = new AirportRouteRequest();
        request.setOriginAirportCode("CGK");
        request.setDestinationAirportCode("NRT");
        request.setHours(4);
        request.setMinutes(10);

        // from Airport
        Mockito.when(airportRepository.findById(request.getOriginAirportCode()))
                .thenReturn(Optional.of(AirportDummies.airports().get(0)));

        // to Airport
        Mockito.when(airportRepository.findById(request.getDestinationAirportCode()))
                .thenReturn(Optional.of(AirportDummies.airports().get(1)));

        Mockito.when(airportRouteRepository.save(FlightRouteDummies.flightRoute().get(0)))
                .thenReturn(FlightRouteDummies.flightRoute().get(0));

        var actualValue = flightRouteService.addAirportRoute(request);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testUpdateRoute() {
        String routeCode = "CGK-NRT";
        UpdateRouteRequest request = new UpdateRouteRequest();
        request.setHours(1);
        request.setMinutes(10);
        request.setOriginAirportCode("CGK");
        request.setDestinationAirportCode("NRT");

        Mockito.when(airportRouteRepository.findById(routeCode))
                .thenReturn(Optional.of(FlightRouteDummies.flightRoute().get(0)));

        // from Airport
        Mockito.when(airportRepository.findById(request.getOriginAirportCode()))
                .thenReturn(Optional.of(AirportDummies.airports().get(0)));

        // to Airport
        Mockito.when(airportRepository.findById(request.getDestinationAirportCode()))
                .thenReturn(Optional.of(AirportDummies.airports().get(1)));

        var actualValue = flightRouteService.updateRoute(routeCode, request);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testDeleteById() {
        String routeCode = "CGK-NRT";

        Mockito.when(airportRouteRepository.findById(routeCode))
                .thenReturn(Optional.of(FlightRouteDummies.flightRoute().get(0)));

        var actualValue = flightRouteService.deleteById(routeCode);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testGetAllRoute() {
        List<FlightRouteDetailDTO> ls = FlightRouteDummies.flightRoute()
                        .stream()
                .map(it -> {
                    FlightRouteDetailDTO dto = new FlightRouteDetailDTO();
                    dto.setHours(it.getHours());
                    dto.setMinutes(it.getMinutes());
                    dto.setOriginAirportCode(it.getFromAirport().IATACode);
                    dto.setDestinationAirportCode(it.getToAirport().IATACode);
                    return dto;
                }).toList();

        Mockito.when(airportRouteRepository.findAllRoute())
                .thenReturn(ls);

        var actualValue = flightRouteService.getAllRoute();
        var expectedSize = 2;
        var expectedHours = 4;
        var expectedMinutes = 10;
        var expectedOriginAirport = "CGK";
        var expectedDestinationAirport = "NRT";

        Assertions.assertEquals(expectedSize, actualValue.size());
        Assertions.assertEquals(expectedHours, actualValue.get(0).getHours());
        Assertions.assertEquals(expectedMinutes, actualValue.get(0).getMinutes());
        Assertions.assertEquals(expectedOriginAirport, actualValue.get(0).getOriginAirportCode());
        Assertions.assertEquals(expectedDestinationAirport, actualValue.get(0).getDestinationAirportCode());
    }

    @Test
    void getRouteDetailById() {
        String routeCode = "CGK-NRT";

        FlightRouteDetailDTO dto = new FlightRouteDetailDTO();
        dto.setHours(FlightRouteDummies.flightRoute().get(0).getHours());
        dto.setMinutes(FlightRouteDummies.flightRoute().get(0).getMinutes());
        dto.setOriginAirportCode(FlightRouteDummies.flightRoute().get(0).getFromAirport().IATACode);
        dto.setDestinationAirportCode(FlightRouteDummies.flightRoute().get(0).getToAirport().IATACode);

        Mockito.when(airportRouteRepository.findRouteDetailById(routeCode))
                .thenReturn(Optional.of(dto));

        var actualValue = flightRouteService.getRouteDetailById(routeCode);
        var expectedHours = 4;
        var expectedMinutes = 10;
        var expectedOriginAirport = "CGK";
        var expectedDestinationAirport = "NRT";

        Assertions.assertEquals(expectedHours, actualValue.getHours());
        Assertions.assertEquals(expectedMinutes, actualValue.getMinutes());
        Assertions.assertEquals(expectedOriginAirport, actualValue.getOriginAirportCode());
        Assertions.assertEquals(expectedDestinationAirport, actualValue.getDestinationAirportCode());
    }
}