package com.binar.flyket.model;

import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;

public enum BookingStatus {
   EXPIRED, WAITING, ACTIVE, COMPLETED;

    public static BookingStatus getStatus (String statusName) {
        if (statusName.trim().equalsIgnoreCase("waiting")) {
            return BookingStatus.WAITING;
        } else if (statusName.trim().equalsIgnoreCase("expired")) {
            return BookingStatus.EXPIRED;
        } else if (statusName.trim().equalsIgnoreCase("active")) {
            return BookingStatus.ACTIVE;
        } else if (statusName.trim().equalsIgnoreCase("completed")) {
            return BookingStatus.COMPLETED;
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Booking Status" + Constants.NOT_FOUND_MSG);
    }

}
