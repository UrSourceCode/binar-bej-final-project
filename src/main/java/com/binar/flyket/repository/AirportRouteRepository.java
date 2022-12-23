package com.binar.flyket.repository;

import com.binar.flyket.dto.model.FlightRouteDetailDTO;
import com.binar.flyket.model.FlightRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.binar.flyket.repository.query.RouteQuery.ROUTE_DETAIL_BY_ID;
import static com.binar.flyket.repository.query.RouteQuery.ROUTE_DETAIL_INNER_JOIN;


@Repository
public interface AirportRouteRepository extends JpaRepository<FlightRoute, String> {

    @Query(value = ROUTE_DETAIL_INNER_JOIN)
    List<FlightRouteDetailDTO> findAllRoute();

    @Query(value = ROUTE_DETAIL_BY_ID)
    Optional<FlightRouteDetailDTO> findRouteDetailById(String routeId);
}
