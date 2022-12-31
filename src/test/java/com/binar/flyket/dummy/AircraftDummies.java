package com.binar.flyket.dummy;

import com.binar.flyket.model.Aircraft;

import java.util.List;

public class AircraftDummies {

    public static List<Aircraft> aircraftList() {
        Aircraft ac1 = new Aircraft();
        ac1.setId(1);
        ac1.setType("Boeing 777-300ER");

        Aircraft ac2 = new Aircraft();
        ac2.setId(2);
        ac2.setType("Boeing 737-800NG");

        return List.of(ac1, ac2);
    }
}
