package com.binar.flyket.repository.query;

public class RouteQuery {

    public final static String  ROUTE_DETAIL_INNER_JOIN = "SELECT " +
            "new com.binar.flyket.dto.model.AirportRouteDetailDTO(arr.id, " +
                        "new com.binar.flyket.dto.model.AirportDetailDTO(fa.IATACode, fa.name, fa.city, fa.country.name), " +
                        "new com.binar.flyket.dto.model.AirportDetailDTO(ta.IATACode, ta.name, ta.city, ta.country.name), " +
                        "arr.hours, arr.minutes) " +
            "FROM AirportRoute AS arr " +
            "JOIN arr.fromAirport AS fa WHERE arr.fromAirport.IATACode = fa.IATACode " +
            "JOIN arr.toAirport AS ta WHERE arr.toAirport.IATACode = ta.IATACode";
}
