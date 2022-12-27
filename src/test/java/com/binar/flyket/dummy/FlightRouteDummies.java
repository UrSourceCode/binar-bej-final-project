package com.binar.flyket.dummy;

import com.binar.flyket.model.FlightRoute;

import java.time.LocalDateTime;
import java.util.List;

public class FlightRouteDummies {

    public static List<FlightRoute> flightRoute() {
        FlightRoute fr = new FlightRoute();
        fr.setId("CGK-NRT");
        fr.setFromAirport(AirportDummies.airports().get(0));
        fr.setToAirport(AirportDummies.airports().get(1));
        fr.setHours(4);
        fr.setMinutes(10);
        fr.setUpdatedAt(LocalDateTime.now());
        fr.setCreatedAt(LocalDateTime.now());

        return List.of(fr);
    }
}
