package com.binar.flyket.repository.query;

public class AircraftDetailQuery {

    public static final String AIRCRAFT_DETAIL_JOIN = "SELECT "+
            "new com.binar.flyket.dto.model.AircraftDetailDTO(acd.id, acd.aircraftClass, acd.price, acd.maxBaggage, acd.maxCabin, ac.type) " +
            "FROM AircraftDetail AS acd " +
            "JOIN acd.aircraft AS ac " +
            "WHERE acd.aircraft.id = ac.id" ;
}
