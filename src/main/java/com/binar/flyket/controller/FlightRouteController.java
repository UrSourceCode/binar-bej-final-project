package com.binar.flyket.controller;

import com.binar.flyket.dto.request.AirportRouteRequest;
import com.binar.flyket.dto.request.UpdateRouteRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.FlightRouteService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Tag(name = "Flight Route")
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/routes")
public class FlightRouteController {

    private FlightRouteService flightRouteService;

    public FlightRouteController(FlightRouteService flightRouteService) {
        this.flightRouteService = flightRouteService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addRoute(@RequestBody AirportRouteRequest airportRouteRequest) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG,
                    flightRouteService.addAirportRoute(airportRouteRequest)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRoute(@RequestParam("id") String id) {
        try {
            flightRouteService.deleteById(id);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.DELETED_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRoute() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                Constants.SUCCESS_MSG, flightRouteService.getAllRoute()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateRouteById(
            @RequestParam("routeId") String routeId, @RequestBody UpdateRouteRequest updateRouteRequest) {
        try {
            flightRouteService.updateRoute(routeId, updateRouteRequest);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.UPDATED_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<?> getRouteDetailById(@PathVariable String routeId) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG,  flightRouteService.getRouteDetailById(routeId)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }
}
