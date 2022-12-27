package com.binar.flyket.dummy;

import com.binar.flyket.model.FlightRoute;

import java.time.LocalDateTime;
import java.util.List;

public class FlightRouteDummies {

    public static List<FlightRoute> flightRoute() {
        FlightRoute fr1 = new FlightRoute();
        fr1.setId("CGK-NRT");
        fr1.setFromAirport(AirportDummies.airports().get(0));
        fr1.setToAirport(AirportDummies.airports().get(1));
        fr1.setHours(4);
        fr1.setMinutes(10);
        fr1.setUpdatedAt(LocalDateTime.now());
        fr1.setCreatedAt(LocalDateTime.now());

        FlightRoute fr2 = new FlightRoute();
        fr2.setId("NRT-CGK");
        fr2.setFromAirport(AirportDummies.airports().get(1));
        fr2.setToAirport(AirportDummies.airports().get(0));
        fr2.setHours(4);
        fr2.setMinutes(10);
        fr2.setUpdatedAt(LocalDateTime.now());
        fr2.setCreatedAt(LocalDateTime.now());

        return List.of(fr1, fr2);
    }
}
