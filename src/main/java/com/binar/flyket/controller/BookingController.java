package com.binar.flyket.controller;

import com.binar.flyket.dto.request.BookingRequest;
import com.binar.flyket.dto.request.PaymentRequest;
import com.binar.flyket.dto.request.ValidateBookingRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.BookingService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api")
@Tag(name = "Booking")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasRole('BUYER') or hasRole('ADMIN')")
    @GetMapping("/booking/detail/{booking_id}")
    public ResponseEntity<?> getBookingDetail(@PathVariable("booking_id") String bookingId) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG,
                    bookingService.getBookingDetail(bookingId)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('BUYER') or hasRole('ADMIN')")
    @PostMapping("/booking/add")
    public ResponseEntity<?> addBooking(@RequestParam("uid") String userId,
                                        @RequestBody BookingRequest bookingRequest) {
        try {
             return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                     new Date(), Constants.SUCCESS_MSG, bookingService.addBooking(userId, bookingRequest)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        } catch (FlyketException.InputIsEmptyException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('BUYER') or hasRole('ADMIN')")
    @PostMapping("/booking/set-payment")
    public ResponseEntity<?> setPayment(@RequestBody PaymentRequest request) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                    new Date(), Constants.SUCCESS_MSG, bookingService.setPaymentMethod(request)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        } catch (FlyketException.BookingExpiredException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/booking/validate")
    public ResponseEntity<?> validateBooking(
            @RequestBody ValidateBookingRequest validateBookingRequest) {
        try {
            bookingService.validateBooking(validateBookingRequest.getUserId(), validateBookingRequest.getBookingId());
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        } catch (FlyketException.BookingExpiredException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('BUYER') or hasRole('ADMIN')")
    @GetMapping("/booking/show-seat")
    public ResponseEntity<?> showSeats(@RequestParam("schedule-id") String scheduleId) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG,
                    bookingService.showSeat(scheduleId)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/booking/validate-list")
    public ResponseEntity<?> validateBookingList(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "size") int size,
            @RequestParam(defaultValue = "newest", value = "created-at") String sort) {
        Pageable paging = PageRequest.of(page, size, Sort.by(Constants.sortDirection(sort), "createdAt"));
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG,
                bookingService.validateBookingList(paging)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/booking/status")
    public ResponseEntity<?> searchByStatus(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "size") int size,
            @RequestParam(defaultValue = "completed", value = "booking-status") String bookingStatus,
            @RequestParam(defaultValue = "newest", value = "created-at") String sort) {
        Pageable paging = PageRequest.of(page, size, Sort.by(Constants.sortDirection(sort), "createdAt"));
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG,
                bookingService.findByStatus(bookingStatus, paging)));
    }

    @GetMapping("/booking/check-status-booking")
    public ResponseEntity<?> checkStatusBooking(@RequestParam("booking-id") String bookingId) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG,
                    bookingService.bookingStatus(bookingId)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

}
