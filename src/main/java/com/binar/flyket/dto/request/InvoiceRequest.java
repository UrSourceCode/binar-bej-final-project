package com.binar.flyket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor@NoArgsConstructor
public class InvoiceRequest {
    private String userId;
    private String bookingId;
}
