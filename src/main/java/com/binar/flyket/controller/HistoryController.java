package com.binar.flyket.controller;

import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.BookingStatus;
import com.binar.flyket.service.TransactionHistoryService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/history")
@Tag(name = "History")
public class HistoryController {

    private TransactionHistoryService transactionHistoryService;

    public HistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }


    @GetMapping("/booking/{userid}")
    public ResponseEntity<?> getAllRecentOrderByUser(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "size") int size,
            @RequestParam(defaultValue = "", value = "booking-status-filter") String bookingStatusFilter,
            @PathVariable(value = "userid") String userId) {
        try {

            Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());

            if(bookingStatusFilter.equalsIgnoreCase("completed")
                    || bookingStatusFilter.equalsIgnoreCase("waiting")
                    || bookingStatusFilter.equalsIgnoreCase("expired")
                    || bookingStatusFilter.equalsIgnoreCase("active")) {
                return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                        new Date(), Constants.SUCCESS_MSG, transactionHistoryService.getRecentOrder(bookingStatusFilter, userId, paging)));
            }

            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                    new Date(), Constants.SUCCESS_MSG, transactionHistoryService.getRecentOrder(userId, paging)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping("/ticket/detail/{booking-id}")
    public ResponseEntity<?> getRecentOrderDetailByUser(
            @PathVariable(value = "booking-id") String bookingId) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                    new Date(), Constants.SUCCESS_MSG, transactionHistoryService.getRecentOrderDetail(bookingId)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping("/booking/get-all")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "size") int size,
            @RequestParam(defaultValue = "", value = "booking-status-filter") String bookingStatusFilter,
            @RequestParam(defaultValue = "asc", value = "booking-date-sort") String bookingDate) {

        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                new Date(), Constants.SUCCESS_MSG, transactionHistoryService.getAllBookingHistory(
                        page, size,
                        bookingStatusFilter, bookingDate)));
    }

    @GetMapping("/booking/sort-by-status")
    public ResponseEntity<?> sortByStatus(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "size") int size,
            @RequestParam(defaultValue = "asc", value = "booking-status-sort") String bookingStatus) {

        Sort sort;

        if(bookingStatus.equalsIgnoreCase("asc")) {
            sort = Sort.by("bookingStatus").ascending();
        } else if(bookingStatus.equalsIgnoreCase("desc"))  {
            sort = Sort.by("bookingStatus").descending();
        } else {
            sort = Sort.by("bookingStatus").ascending();
        }

        Pageable paging = PageRequest.of(page, size, sort);
        return null;
//        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
//                new Date(), Constants.SUCCESS_MSG, transactionHistoryService.getAllBookingHistory(paging)));
    }

//    @GetMapping("/booking/sort-by-date")
    public ResponseEntity<?> sortByDate( @RequestParam(defaultValue = "0", value = "page") int page,
                                         @RequestParam(defaultValue = "10", value = "size") int size,
                                         @RequestParam(defaultValue = "asc", value = "booking-date-sort") String bookingDate) {

        Sort sort;

        if(bookingDate.equalsIgnoreCase("asc")) {
            sort = Sort.by("createdAt").ascending();
        } else if(bookingDate.equalsIgnoreCase("desc"))  {
            sort = Sort.by("createdAt").descending();
        } else {
            sort = Sort.by("createdAt").ascending();
        }

        Pageable paging = PageRequest.of(page, size, sort);
        return null;
//        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
//                new Date(), Constants.SUCCESS_MSG, transactionHistoryService.getAllBookingHistory(paging)));
    }

}
