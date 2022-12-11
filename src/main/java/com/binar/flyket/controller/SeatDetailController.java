package com.binar.flyket.controller;

import com.binar.flyket.dto.request.SeatRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.SeatDetailService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(value = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/seats")
@Tag(name = "Seat")
public class SeatDetailController {

    private SeatDetailService seatDetailService;

    public SeatDetailController(SeatDetailService seatDetailService) {
        this.seatDetailService = seatDetailService;
    }

    @PostMapping("/add")
    private ResponseEntity<?> addSeat(@RequestBody SeatRequest request) {
        try {
            seatDetailService.addSeat(request);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping
    private ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, seatDetailService.getAll()));
    }
}
