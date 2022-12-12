package com.binar.flyket.dto.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyOrderDetailDTO {
    private String bookingId;
    private List<PassengerTicketList> passengerTicketLists;
}
