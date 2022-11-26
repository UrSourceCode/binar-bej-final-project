package com.binar.flyket.repository.query;

public class AirportQuery {

    public static final String AIRPORT_DETAIL_BY_ID = "SELECT " +
            "new com.binar.flyket.dto.model.AirportDetailDTO(ar.IATACode, ar.name, ar.city, c.name) " +
            "FROM Airport AS ar JOIN ar.country AS c " +
            "WHERE ar.IATACode = :IATACode";

    public static final String AIRPORT_DETAIL_INNER_JOIN = "SELECT " +
            "new com.binar.flyket.dto.model.AirportDetailDTO(ar.IATACode, ar.name, ar.city, c.name) " +
            "FROM Airport AS ar INNER JOIN ar.country AS c "+
            "WHERE ar.country.code = c.code";
}
