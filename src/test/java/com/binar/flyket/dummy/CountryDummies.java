package com.binar.flyket.dummy;

import com.binar.flyket.model.Country;

import java.util.List;

public class CountryDummies {

    public static List<Country> countryList() {
        Country c1 = new Country();
        c1.setName("Indonesia");
        c1.setCode("ID");

        Country c2 = new Country();
        c2.setName("Japan");
        c2.setCode("JP");

        return List.of(c1, c2);
    }


}
