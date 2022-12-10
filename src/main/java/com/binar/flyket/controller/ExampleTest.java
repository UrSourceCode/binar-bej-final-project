package com.binar.flyket.controller;


import com.binar.flyket.dto.request.PassengerRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class ExampleTest {


    @PostMapping("/add")
    public String testBooking(@RequestBody PassengerRequest request) {
        return "";
    }
}
