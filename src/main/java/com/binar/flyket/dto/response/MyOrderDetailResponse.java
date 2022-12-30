package com.binar.flyket.dto.response;

import com.binar.flyket.dto.model.MyOrderDetailDTO;
import com.binar.flyket.dto.model.PassengerTicketList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class MyOrderDetailResponse {
    private MyOrderDetailDTO orderDetail;
    private List<PassengerTicketList> passengerTicketLists;
}
