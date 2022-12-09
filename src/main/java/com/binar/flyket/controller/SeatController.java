package com.binar.flyket.controller;

import com.binar.flyket.dto.model.SeatDTO;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.SeatNo;
import com.binar.flyket.service.SeatService;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;



@Deprecated(since = "move to Seat Detail")
//@CrossOrigin(value = "*", maxAge = 3600L)

@CrossOrigin(value = "*", maxAge = 3600L)

//@RestController
//@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public ResponseEntity<?> getSeats() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                Constants.SUCCESS_MSG, seatService.getSeats()));
    }

    @PostMapping("/add-all")
    public ResponseEntity<?> addSeats(@RequestBody List<SeatDTO> seats) {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                Constants.SUCCESS_MSG, seatService.addSeats(seats)));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSeat(@RequestBody SeatDTO seat) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, seatService.addSeat(seat)));
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSeat(@RequestBody SeatNo seatId) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, seatService.deleteSeat(seatId)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSeatById(@RequestBody SeatNo seatId) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, seatService.getSeatById(seatId)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

}