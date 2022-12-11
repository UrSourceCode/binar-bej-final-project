package com.binar.flyket.service;

import com.binar.flyket.dto.model.BookingDTO;
import com.binar.flyket.dto.model.MyOrderDTO;
import com.binar.flyket.dto.model.MyOrderDetailDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionHistoryService {

    List<MyOrderDTO> getRecentOrder(String userId, Pageable pageable);

    MyOrderDetailDTO getRecentOrderDetail(String bookingId);

    List<BookingDTO> getAllBookingHistory(Pageable pageable);

}
