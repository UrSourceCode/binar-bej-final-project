package com.binar.flyket.controller;


import com.binar.flyket.dto.request.PaymentMethodRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.PaymentMethodService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/payments")
public class PaymentMethodController {

    private PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody PaymentMethodRequest request) {
        try {
            paymentMethodService.addPaymentMethod(request);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") String id) {
        try {
            paymentMethodService.deletePaymentMethod(id);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                new Date(),
                Constants.SUCCESS_MSG,
                paymentMethodService.getAll()));
    }
}
