package com.binar.flyket.controller;

import com.binar.flyket.dto.request.InputAirportRequest;
import com.binar.flyket.dto.request.UpdateAirportRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.AirportService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Tag(name = "Airport")
@CrossOrigin(value = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<?> getAirports() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                Constants.SUCCESS_MSG, airportService.getAirports()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addAirport(@RequestBody @Valid InputAirportRequest inputAirportRequest) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, airportService.addAirport(inputAirportRequest)));
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAirportById(@RequestParam("code") String code) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, airportService.deleteAirportById(code)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping("/{IATACode}")
    public ResponseEntity<?> getAirportById(@PathVariable("IATACode") String IATACode) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, airportService.getAirportById(IATACode)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateAirportById(
            @RequestParam String IATACode,
            @RequestBody UpdateAirportRequest updateAirportRequest) {

        try {
            airportService.updateAirport(IATACode, updateAirportRequest);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }
}
