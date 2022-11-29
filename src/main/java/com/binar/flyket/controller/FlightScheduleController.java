package com.binar.flyket.controller;

import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.FlightScheduleService;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/flight/schedules")
public class FlightScheduleController {

    private FlightScheduleService flightScheduleService;

    public FlightScheduleController(FlightScheduleService flightScheduleService) {
        this.flightScheduleService = flightScheduleService;
    }


    @GetMapping
    public ResponseEntity<?> getFlightSchedules() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                Constants.SUCCESS_MSG,
                flightScheduleService.getFlightScheduleDetails()));
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


    // TODO: udpate
}
