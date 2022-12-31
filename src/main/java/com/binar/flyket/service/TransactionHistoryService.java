package com.binar.flyket.service;


import com.binar.flyket.dto.model.BookingDTO;

import com.binar.flyket.dto.model.BookingHistoryDTO;
import com.binar.flyket.dto.model.MyOrderDTO;
import com.binar.flyket.dto.model.MyOrderDetailDTO;
import com.binar.flyket.dto.response.MyOrderDetailResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionHistoryService {

    List<MyOrderDTO> getRecentOrder(String userId, Pageable pageable);
    List<MyOrderDTO> getRecentOrder(String bookingStatus, String userId, Pageable pageable);

    MyOrderDetailResponse getRecentOrderDetail(String bookingId);

    List<BookingHistoryDTO> getAllBookingHistory(Integer page, Integer size, String bookingStatusFilter, String bookingDate);

}
