package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.dto.request.FlightScheduleRequest;
import com.binar.flyket.dto.request.UpdateScheduleRequest;
import com.binar.flyket.dummy.AircraftDetailDummies;
import com.binar.flyket.dummy.FlightRouteDummies;
import com.binar.flyket.model.*;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

        Assertions.assertNotNull(actualValue);
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
        LocalDate flightDate = LocalDate.now();
        String originAirportId = "CGK";
        String destinationAirportId = "NRT";
        String aircraftClass = "BUSINESS";

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

        List<FlightScheduleDetailDTO> result = new ArrayList<>();
        int size = 10;
        for(int i = 0; i < size; i++) {
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

            result.add(dto);
        }

        Pageable pageable = PageRequest.of(0, 10);

        PageImpl<FlightScheduleDetailDTO> page = new PageImpl<>(result);

        AircraftClass arClass = AircraftClass.getClass(aircraftClass);

        Mockito.when(flightScheduleRepository.searchFlightScheduleByAirportAndDate(originAirportId.toUpperCase().trim(),
                destinationAirportId.toUpperCase().trim(), flightDate, arClass, Status.ACTIVE, pageable))
                .thenReturn(page);

        var actualValue = flightScheduleService.searchFlightSchedule(originAirportId,
                destinationAirportId, aircraftClass, flightDate, pageable);
        var expectedSize = 10;
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

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedSize, actualValue.size());
        Assertions.assertEquals(expectedHours, actualValue.get(0).getHours());
        Assertions.assertEquals(expectedClass, actualValue.get(0).getAircraftClass());
        Assertions.assertEquals(expectedMaxBaggage, actualValue.get(0).getMaxBaggage());
        Assertions.assertEquals(expectedAircraftType, actualValue.get(0).getAircraftType());
        Assertions.assertEquals(expectedArrivalTime, actualValue.get(0).getArrivalTime());
        Assertions.assertEquals(expectedDepartureTime, actualValue.get(0).getDepartureTime());
        Assertions.assertEquals(expectedFlightScheduleId, actualValue.get(0).getFlightScheduleId());
        Assertions.assertEquals(expectedDestinationAirportCode, actualValue.get(0).getDestinationAirportCode());
        Assertions.assertEquals(expectedOriginAirportCity, actualValue.get(0).getOriginAirportCity());
        Assertions.assertEquals(expectedPrice, actualValue.get(0).getPrice());
        Assertions.assertEquals(expectedOriginAirportCode, actualValue.get(0).getOriginAirportCode());
        Assertions.assertEquals(expectedDestinationAirportCity, actualValue.get(0).getDestinationAirportCity());
    }

    @Test
    void testUpdateFlightSchedule() {
        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        String fsId = "sc-" + randId[0] + randId[1];

        UpdateScheduleRequest request = new UpdateScheduleRequest();
        request.setFlightRouteId("CGK-NRT");
        request.setAircraftDetailId("acd3");
        request.setArrivalTime(LocalDateTime.now());
        request.setDepartureTime(LocalDateTime.now());

        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        FlightSchedule fs = new FlightSchedule();
        fs.setId(fsId);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        Mockito.when(aircraftDetailRepository.findById(request.getAircraftDetailId()))
                .thenReturn(Optional.of(acd));
        Mockito.when(airportRouteRepository.findById(request.getFlightRouteId()))
                .thenReturn(Optional.of(fr));
        Mockito.when(flightScheduleRepository.findById(fs.getId()))
                .thenReturn(Optional.of(fs));

        var actualValue = flightScheduleService.updateFlightSchedule(fsId, request);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }
}