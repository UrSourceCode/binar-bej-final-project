package com.binar.flyket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class PassengerRequest {
    private String title;
    private String name;
    private SeatNoRequest seatNoRequest;
}
