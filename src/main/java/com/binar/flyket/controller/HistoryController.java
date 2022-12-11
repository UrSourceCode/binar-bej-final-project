package com.binar.flyket.controller;

import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.TransactionHistoryService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @PathVariable(value = "userid") String userId) {
        try {
            Pageable paging = PageRequest.of(page, size, Sort.by("updatedAt"));
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                    new Date(), Constants.SUCCESS_MSG, transactionHistoryService.getRecentOrder(userId, paging)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping("/booking/{booking-id}/detail")
    public ResponseEntity<?> getRecentOrderDetailByUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @PathVariable(value = "booking-id") String bookingId) {
        try {
            Pageable paging = PageRequest.of(page, size, Sort.by("updatedAt"));
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                    new Date(), Constants.SUCCESS_MSG, transactionHistoryService.getRecentOrderDetail(bookingId, paging)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping("/booking/get-all")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                new Date(), Constants.SUCCESS_MSG, transactionHistoryService.getAllBookingHistory(paging)));
    }
}