package com.binar.flyket.controller;

import com.binar.flyket.dto.request.FlightScheduleRequest;
import com.binar.flyket.dto.request.UpdateScheduleRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.FlightScheduleService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;

@Tag(name = "Flight Schedule")
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/schedules")
public class FlightScheduleController {

    private FlightScheduleService flightScheduleService;

    public FlightScheduleController(FlightScheduleService flightScheduleService) {
        this.flightScheduleService = flightScheduleService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "4", value = "size") int size) {

        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                Constants.SUCCESS_MSG, flightScheduleService.getFlightScheduleDetails(paging)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFlightSchedulesById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG,
                    flightScheduleService.getFlightScheduleDetailById(id)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(), e.getMessage()), e.getStatusCode());
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScheduleById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG,
                    flightScheduleService.deleteFlightScheduleById(id)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addFlightSchedule(@RequestBody @Valid FlightScheduleRequest flightScheduleRequest) {
        try {

            flightScheduleService.addFlightSchedule(flightScheduleRequest);

            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(),
                    e.getMessage()), e.getStatusCode());
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(),
                    e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping("/departure-date")
    public ResponseEntity<?> searchFlightScheduleByAirportAndDate(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "4", value = "size") int size,
            @RequestParam(defaultValue = "earliest", value = "sort_by_departure") String sortBy,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate,
            @RequestParam("originAirportId") String originAirportId,
            @RequestParam("destinationAirportId") String destinationAirportId,
            @RequestParam("aircraftClass") String aircraftClass) {
        try {

            Sort sort = sortBy.equalsIgnoreCase("earliest") ?
                    Sort.by("departureTime").ascending() : Sort.by("departureTime").descending();

            Pageable paging = PageRequest.of(page, size, sort);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG,
                    flightScheduleService.searchFlightSchedule(
                            originAirportId, destinationAirportId, aircraftClass, flightDate, paging)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(),
                    e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateScheduleAirport(
            @RequestParam("scheduleId") String scheduleId,
            @RequestBody UpdateScheduleRequest updateScheduleRequest) {
        try {
            flightScheduleService.updateFlightSchedule(scheduleId, updateScheduleRequest);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(),
                    e.getMessage()), e.getStatusCode());
        }
    }
}
