package com.binar.flyket.service;

import com.binar.flyket.dto.model.BookingDTO;
import com.binar.flyket.dto.model.MyOrderDTO;
import com.binar.flyket.dto.model.MyOrderDetailDTO;

import java.util.List;

public interface TransactionHistory {

    List<MyOrderDTO> getRecentOrder(String userId);

    MyOrderDetailDTO getRecentOrderDetail(String bookingId);

    List<BookingDTO> getAllBookingHistory();

}
