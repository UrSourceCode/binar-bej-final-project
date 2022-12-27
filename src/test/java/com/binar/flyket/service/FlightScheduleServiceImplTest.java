package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.dto.request.FlightScheduleRequest;
import com.binar.flyket.dummy.AircraftDetailDummies;
import com.binar.flyket.dummy.FlightRouteDummies;
import com.binar.flyket.model.AircraftDetail;
import com.binar.flyket.model.FlightRoute;
import com.binar.flyket.model.FlightSchedule;
import com.binar.flyket.model.Status;
import com.binar.flyket.repository.AircraftDetailRepository;
import com.binar.flyket.repository.AirportRouteRepository;
import com.binar.flyket.repository.FlightScheduleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

class FlightScheduleServiceImplTest {

    @Mock
    private FlightScheduleRepository flightScheduleRepository;

    @Mock
    private AirportRouteRepository airportRouteRepository;

    @Mock
    private AircraftDetailRepository aircraftDetailRepository;

    @InjectMocks
    private FlightScheduleServiceImpl flightScheduleService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddFlightSchedule() {
        FlightScheduleRequest request = new FlightScheduleRequest();
        request.setFlightDate(LocalDate.now());
        request.setArrivalTime(LocalDateTime.now());
        request.setDepartureTime(LocalDateTime.now());
        request.setRouteId("CGK-NRT");
        request.setAircraftDetailId("acd3");

        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(request.getArrivalTime());
        fs.setDepartureTime(request.getDepartureTime());
        fs.setCreatedAt(LocalDateTime.now());

        Mockito.when(aircraftDetailRepository.findById(request.getAircraftDetailId()))
                .thenReturn(Optional.of(acd));
        Mockito.when(airportRouteRepository.findById(request.getRouteId()))
                .thenReturn(Optional.of(fr));
        Mockito.when(flightScheduleRepository.save(fs))
                .thenReturn(fs);

        var actualValue = flightScheduleService.addFlightSchedule(request);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testDeleteFlightScheduleById() {
        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.DELETE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        Mockito.when(flightScheduleRepository.findById(fs.getId()))
                .thenReturn(Optional.of(fs));

        var actualValue = flightScheduleService.deleteFlightScheduleById(fs.getId());
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testGetFlightScheduleDetailById() {
        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        FlightScheduleDetailDTO dto = new FlightScheduleDetailDTO();
        dto.setFlightScheduleId(fs.getId());
        dto.setArrivalTime(fs.getArrivalTime());
        dto.setDepartureTime(fs.getDepartureTime());
        dto.setHours(fs.getFlightRoute().getHours());
        dto.setMinutes(fs.getFlightRoute().getMinutes());
        dto.setAircraftClass(fs.getAircraftDetail().getAircraftClass());
        dto.setAircraftType(fs.getAircraftDetail().getAircraft().getType());
        dto.setMaxBaggage(fs.getAircraftDetail().getMaxBaggage());
        dto.setPrice(fs.getAircraftDetail().getPrice());
        dto.setMaxCabin(fs.getAircraftDetail().getMaxCabin());
        dto.setDestinationAirportCity(fs.getFlightRoute().getToAirport().getCity());
        dto.setDestinationAirportCode(fs.getFlightRoute().getToAirport().IATACode);
        dto.setOriginAirportCity(fs.getFlightRoute().getFromAirport().getCity());
        dto.setOriginAirportCode(fs.getFlightRoute().getToAirport().IATACode);

        Mockito.when(flightScheduleRepository.findFlightScheduleDetailById(fs.getId(), Status.ACTIVE))
                .thenReturn(Optional.of(dto));

        var actualValue = flightScheduleService.getFlightScheduleDetailById(fs.getId());
        var expectedPrice = fs.getAircraftDetail().getPrice();
        var expectedHours = fs.getFlightRoute().getHours();
        var expectedMaxBaggage = fs.getAircraftDetail().getMaxBaggage();
        var expectedOriginAirportCity = fs.getFlightRoute().getFromAirport().getCity();
        var expectedOriginAirportCode = fs.getFlightRoute().getToAirport().IATACode;
        var expectedDestinationAirportCity = fs.getFlightRoute().getToAirport().getCity();
        var expectedDestinationAirportCode =  fs.getFlightRoute().getToAirport().IATACode;
        var expectedClass = fs.getAircraftDetail().getAircraftClass();
        var expectedFlightScheduleId = fs.getId();
        var expectedArrivalTime = fs.getArrivalTime();
        var expectedDepartureTime = fs.getDepartureTime();
        var expectedAircraftType = fs.getAircraftDetail().getAircraft().getType();

        Assertions.assertEquals(expectedHours, actualValue.getHours());
        Assertions.assertEquals(expectedClass, actualValue.getAircraftClass());
        Assertions.assertEquals(expectedMaxBaggage, actualValue.getMaxBaggage());
        Assertions.assertEquals(expectedAircraftType, actualValue.getAircraftType());
        Assertions.assertEquals(expectedArrivalTime, actualValue.getArrivalTime());
        Assertions.assertEquals(expectedDepartureTime, actualValue.getDepartureTime());
        Assertions.assertEquals(expectedFlightScheduleId, actualValue.getFlightScheduleId());
        Assertions.assertEquals(expectedDestinationAirportCode, actualValue.getDestinationAirportCode());
        Assertions.assertEquals(expectedOriginAirportCity, actualValue.getOriginAirportCity());
        Assertions.assertEquals(expectedPrice, actualValue.getPrice());
        Assertions.assertEquals(expectedOriginAirportCode, actualValue.getOriginAirportCode());
        Assertions.assertEquals(expectedDestinationAirportCity, actualValue.getDestinationAirportCity());
    }

    @Test
    void testSearchFlightSchedule() {

    }

    @Test
    void testUpdateFlightSchedule() {

    }
}