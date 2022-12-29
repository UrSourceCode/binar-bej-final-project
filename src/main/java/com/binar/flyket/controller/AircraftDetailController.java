package com.binar.flyket.controller;


import com.binar.flyket.dto.request.AircraftDetailRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.AircraftDetailService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Tag(name = "Aircraft Detail")
@CrossOrigin(value = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/aircraft/details")
public class AircraftDetailController {

    private AircraftDetailService aircraftDetailService;


    public AircraftDetailController(AircraftDetailService aircraftDetailService) {
        this.aircraftDetailService = aircraftDetailService;
    }

    @GetMapping
    public ResponseEntity<?> getAircraftDetail() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                Constants.SUCCESS_MSG, aircraftDetailService.getAllAircraftDetail()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addAircraftDetail(@RequestBody AircraftDetailRequest request) {
        try {
            aircraftDetailService.addAircraftDetail(request);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAircraftById(@RequestParam("id") String id) {
        try {
            aircraftDetailService.deleteById(id);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }
}
