package com.binar.flyket.controller;


import com.binar.flyket.dto.response.Response;
import com.binar.flyket.service.AircraftDetailService;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/aircraft/details")
public class AircraftDetailController {

    private AircraftDetailService aircraftDetailService;


    public AircraftDetailController(AircraftDetailService aircraftDetailService) {
        this.aircraftDetailService = aircraftDetailService;
    }

    @RequestMapping
    public ResponseEntity<?> getAircraftDetail() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                Constants.SUCCESS_MSG, aircraftDetailService.getAllAircraftDetail()));
    }
}
