package com.binar.flyket.model;

import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;

public enum AircraftClass {
    FIRST, BUSINESS, ECONOMY;

    public static AircraftClass getClass(String className) {
        if(className.trim().equalsIgnoreCase("business")) {
            return AircraftClass.BUSINESS;
        } else if(className.trim().equalsIgnoreCase("first")) {
            return AircraftClass.FIRST;
        } else if(className.trim().equalsIgnoreCase("economy")) {
            return AircraftClass.ECONOMY;
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Class " +Constants.NOT_FOUND_MSG);
    }
}
