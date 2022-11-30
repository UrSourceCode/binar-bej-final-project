package com.binar.flyket.repository.query;


public class RouteQuery {

    public final static String  ROUTE_DETAIL_INNER_JOIN = "SELECT " +
            "new com.binar.flyket.dto.model.FlightRouteDetailDTO(arr.id, fa.IATACode,  ta.IATACode, arr.hours, arr.minutes) " +
            "FROM FlightRoute AS arr " +
            "INNER JOIN arr.fromAirport AS fa " +
            "INNER JOIN arr.toAirport AS ta";

    public final static String  ROUTE_DETAIL_BY_ID = "SELECT " +
            "new com.binar.flyket.dto.model.FlightRouteDetailDTO(arr.id, fa.IATACode,  ta.IATACode, arr.hours, arr.minutes) " +
            "FROM FlightRoute AS arr " +
            "INNER JOIN arr.fromAirport AS fa " +
            "INNER JOIN arr.toAirport AS ta " +
            "WHERE arr.id = :routeId";
}
