package com.binar.flyket.repository;

import com.binar.flyket.dto.model.BookingDTO;
import com.binar.flyket.dto.model.MyOrderDTO;
import com.binar.flyket.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query(value = "SELECT new com.binar.flyket.dto.model.MyOrderDTO(fs.id, " +
            " bk.totalPassenger, fs.arrivalTime, fs.departureTime, fs.flightRoute.fromAirport.IATACode , fs.flightRoute.toAirport.IATACode," +
            " bk.updatedAt, bk.bookingStatus, " +
            " fs.flightRoute.hours, fs.flightRoute.minutes, fs.aircraftDetail.price) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "JOIN bk.paymentMethod AS py " +
            "WHERE bk.user.id = :user_id")
    List<MyOrderDTO> getRecentOrderByUser(@Param("user_id") String userId);

    @Query(value = "SELECT new com.binar.flyket.dto.model.BookingDTO(usr.id, " +
            " usr.email, usr.phoneNumber, bk.id, py.name, bk.amount, bk.bookingStatus, bk.createdAt, bk.updatedAt) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "JOIN bk.user AS usr " +
            "JOIN bk.paymentMethod AS py ")
    List<BookingDTO> getAllBooking();
}
