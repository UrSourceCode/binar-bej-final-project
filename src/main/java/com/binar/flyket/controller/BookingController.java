package com.binar.flyket.controller;

import com.binar.flyket.dto.request.BookingRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Booking")
public class BookingController {


    @PostMapping("/booking/add")
    public ResponseEntity<?> addBooking(@RequestBody BookingRequest bookingRequest) {
        return null;
    }
}
