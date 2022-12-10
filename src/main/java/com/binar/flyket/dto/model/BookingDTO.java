package com.binar.flyket.dto.model;

import com.binar.flyket.model.BookingStatus;
import com.binar.flyket.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class BookingDTO {
    private String userId;
    private String email;
    private String phoneNumber;
    private String bookingId;
    private String paymentMethod;
    private BigDecimal amount;
    private BookingStatus bookingStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
