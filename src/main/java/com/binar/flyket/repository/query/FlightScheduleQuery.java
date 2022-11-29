package com.binar.flyket.repository.query;

public class FlightScheduleQuery {

    public static final String FLIGHT_SCHEDULE_DETAIL_JOIN = "SELECT " +
            "NEW com.binar.flyket.dto.model.FlightScheduleDetailDTO(fs.id, " +
                    "fs.departureTime, fs.arrivalTime, fs.flightDate, acd.aircraftClass, " +
                    "acd.aircraft.type, " +
                    "fr.fromAirport.IATACode, fr.fromAirport.name, fr.fromAirport.city, " +
                    "fr.toAirport.IATACode, fr.toAirport.name, fr.toAirport.city, " +
                    "fr.hours, fr.minutes, acd.price, acd.maxBaggage, acd.maxCabin) " +
            "FROM FlightSchedule AS fs " +
            "JOIN fs.aircraftDetail AS acd " +
            "JOIN fs.flightRoute AS fr " +
            "WHERE fs.aircraftDetail.id = acd.id " +
            "AND fs.flightRoute.id = fr.id ";


    public static final String FLIGHT_SCHEDULE_DETAIL_JOIN_BY_ID = "SELECT " +
            "NEW com.binar.flyket.dto.model.FlightScheduleDetailDTO(fs.id, " +
                "fs.departureTime, fs.arrivalTime, fs.flightDate, acd.aircraftClass, " +
                "acd.aircraft.type, " +
                "fr.fromAirport.IATACode, fr.fromAirport.name, fr.fromAirport.city, " +
                "fr.toAirport.IATACode, fr.toAirport.name, fr.toAirport.city, " +
                "fr.hours, fr.minutes, acd.price, acd.maxBaggage, acd.maxCabin) " +
            "FROM FlightSchedule AS fs " +
            "JOIN fs.aircraftDetail AS acd " +
            "JOIN fs.flightRoute AS fr " +
            "WHERE fs.id = :id ";
}
