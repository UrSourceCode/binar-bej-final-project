package com.binar.flyket.dummy;

import com.binar.flyket.model.Country;

import java.util.List;

public class CountryDummies {

    public static List<Country> countryList() {
        Country c1 = new Country();
        c1.setName("Jakarta");
        c1.setCode("JKT");

        Country c2 = new Country();
        c2.setName("Surabaya");
        c2.setCode("SBY");

        return List.of(c1, c2);
    }


}
