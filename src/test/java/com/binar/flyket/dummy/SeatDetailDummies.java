package com.binar.flyket.dummy;

import com.binar.flyket.model.SeatDetail;

import java.util.List;

public class SeatDetailDummies {

    public static List<SeatDetail> seatDetails() {
        SeatDetail sd1 = new SeatDetail();
        sd1.setStatus(false);
        sd1.setId("1A");

        SeatDetail sd2 = new SeatDetail();
        sd2.setStatus(false);
        sd2.setId("1B");

        SeatDetail sd3 = new SeatDetail();
        sd3.setStatus(false);
        sd3.setId("1C");

        return List.of(sd1, sd2, sd3);
    }
}
