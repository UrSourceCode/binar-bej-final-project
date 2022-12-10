package com.binar.flyket.service;


import com.binar.flyket.dto.request.SeatRequest;
import com.binar.flyket.model.SeatDetail;

import java.util.List;


public interface SeatDetailService {

    Boolean addSeat(SeatRequest request);

    List<SeatDetail> getAll();



}
