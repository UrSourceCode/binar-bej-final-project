package com.binar.flyket.controller;

import com.binar.flyket.dto.request.BookingRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.BookingService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
@Tag(name = "Booking")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking/add")
    public ResponseEntity<?> addBooking(@RequestParam("uid") String userId,
                                        @RequestBody BookingRequest bookingRequest) {
        try {
             return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                     new Date(), Constants.SUCCESS_MSG, bookingService.addBooking(userId, bookingRequest)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }
}
