package com.binar.flyket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String bookingId;
    private String email;
    private String paymentName;
    private BigDecimal price;
}
