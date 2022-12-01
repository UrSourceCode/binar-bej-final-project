package com.binar.flyket.controller;

import com.binar.flyket.dto.model.SearchScheduleRequest;
import com.binar.flyket.dto.request.FlightScheduleRequest;
import com.binar.flyket.dto.request.UpdateScheduleRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.FlightScheduleService;
import com.binar.flyket.utils.Constants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/schedules")
public class FlightScheduleController {

    private FlightScheduleService flightScheduleService;

    public FlightScheduleController(FlightScheduleService flightScheduleService) {
        this.flightScheduleService = flightScheduleService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {

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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestBody SearchScheduleRequest searchScheduleRequest) {

        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG,
                flightScheduleService.searchFlightSchedule(
                        paging,
                        searchScheduleRequest)));
    }

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
