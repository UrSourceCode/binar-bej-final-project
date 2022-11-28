package com.binar.flyket.controller;


import com.binar.flyket.dto.request.AircraftDetailRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.AircraftDetailService;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PostMapping("/add")
    public ResponseEntity<?> addAircraftDetail(@RequestBody AircraftDetailRequest request) {
        try {
            aircraftDetailService.addAircraftDetail(request);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAircraftById(@RequestParam("id") String id) {
        try {
            aircraftDetailService.deleteById(id);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }
}