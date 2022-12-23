package com.binar.flyket.dto.request;

import com.binar.flyket.model.SeatNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRequest {
    private String title;
    private String name;
    private String seatNo;
}
