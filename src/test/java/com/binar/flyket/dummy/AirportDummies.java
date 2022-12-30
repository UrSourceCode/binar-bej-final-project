package com.binar.flyket.dummy;

import com.binar.flyket.model.Airport;

import java.util.List;

public class AirportDummies {


    public static List<Airport> airports() {
        Airport a1 = new Airport();
        a1.setIATACode("CGK");
        a1.setName("Soekarno-Hatta International");
        a1.setCity("Jakarta");

        Airport a2 = new Airport();
        a2.setIATACode("NRT");
        a2.setName("Narita International");
        a2.setCity("Tokyo");

        return List.of(a1, a2);
    }
}
