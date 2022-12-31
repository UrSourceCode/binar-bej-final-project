package com.binar.flyket.repository;

import com.binar.flyket.dto.model.*;
import com.binar.flyket.model.Booking;
import com.binar.flyket.model.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.MyOrderDTO(fs.id, bk.id, " +
            " usr.id, usr.email, usr.phoneNumber, bk.amount, " +
            " bk.totalPassenger, fs.arrivalTime, fs.departureTime, " +
            " fs.flightRoute.fromAirport.IATACode , fs.flightRoute.toAirport.IATACode," +
            " bk.updatedAt, bk.bookingStatus, " +
            " fs.flightRoute.hours, fs.flightRoute.minutes, fs.aircraftDetail.price) " +
            "FROM Booking AS bk " +
            "JOIN bk.user AS usr " +
            "JOIN bk.flightSchedule AS fs " +
            "WHERE bk.user.id = :user_id")
    Page<MyOrderDTO> getRecentOrderByUser(@Param("user_id") String userId, Pageable pageable);
    @Query(value = "SELECT NEW com.binar.flyket.dto.model.MyOrderDTO(fs.id, bk.id, " +
            " usr.id, usr.email, usr.phoneNumber, bk.amount, " +
            " bk.totalPassenger, fs.arrivalTime, fs.departureTime, " +
            " fs.flightRoute.fromAirport.IATACode , fs.flightRoute.toAirport.IATACode," +
            " bk.updatedAt, bk.bookingStatus, " +
            " fs.flightRoute.hours, fs.flightRoute.minutes, fs.aircraftDetail.price) " +
            "FROM Booking AS bk " +
            "JOIN bk.user AS usr " +
            "JOIN bk.flightSchedule AS fs " +
            "WHERE bk.user.id = :user_id " +
            "AND bk.bookingStatus = :status")
    Page<MyOrderDTO> getRecentOrderByUser(@P("booking_status") BookingStatus status,
                                          @Param("user_id") String userId, Pageable pageable);
    @Query(value = "SELECT NEW com.binar.flyket.dto.model.BookingHistoryDTO(fs.id, usr.id, " +
            " usr.email, usr.phoneNumber, bk.id, bk.amount, bk.bookingStatus, bk.createdAt, bk.updatedAt) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "JOIN bk.user AS usr " +
            "WHERE bk.user.id = usr.id " +
            "AND bk.flightSchedule.id = fs.id " +
            "AND bk.user.id = :user_id")
    Page<BookingHistoryDTO> findAllBookingByUser(@Param("user_id") String userId, Pageable pageable);


    @Query(value = "SELECT NEW com.binar.flyket.dto.model.BookingHistoryDTO(fs.id, usr.id, " +
            " usr.email, usr.phoneNumber, bk.id, bk.amount, bk.bookingStatus, bk.createdAt, bk.updatedAt) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "JOIN bk.user AS usr " +
            "WHERE bk.user.id = usr.id " +
            "AND bk.flightSchedule.id = fs.id ")
    Page<BookingHistoryDTO> findAllBooking(Pageable pageable);

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.BookingHistoryDTO(fs.id, usr.id, " +
            " usr.email, usr.phoneNumber, bk.id, bk.amount, bk.bookingStatus, bk.createdAt, bk.updatedAt) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "JOIN bk.user AS usr " +
            "WHERE bk.user.id = usr.id " +
            "AND bk.flightSchedule.id = fs.id " +
            "AND bk.bookingStatus = :booking_status")
    Page<BookingHistoryDTO> findAllBookingByStatus(
            @Param("booking_status") BookingStatus bookingStatus,
            Pageable pageable);

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.BookingDetailDTO(bk.id, fs.flightRoute.fromAirport.IATACode, " +
            "fs.flightRoute.toAirport.IATACode, fs.flightRoute.hours, fs.flightRoute.minutes, bk.amount) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "WHERE bk.id = :booking_id")
    Optional<BookingDetailDTO> getBookingDetail(@Param("booking_id") String bookingId);

    @Query(value = "SELECT bk FROM Booking bk WHERE bk.expiredTime < :current_time AND bk.bookingStatus =:status")
    List<Booking> checkStatusBooking(@Param("current_time") Long currentTime,
                                     @Param("status")BookingStatus bookingStatus);


    @Query(value = "SELECT NEW com.binar.flyket.dto.model.BookingValidateDTO(usr.id, CONCAT(usr.firstName, ' ', usr.lastName), " +
            " usr.email, usr.phoneNumber, bk.id, bk.amount, bk.bookingStatus, bk.createdAt, bk.updatedAt) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "JOIN bk.user AS usr " +
            "WHERE bk.user.id = usr.id " +
            "AND bk.flightSchedule.id = fs.id " +
            "AND bk.bookingStatus = :booking_status")
    Page<BookingValidateDTO> validateBookingList(
            @Param("booking_status") BookingStatus bookingStatus,
            Pageable pageable);

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.BookingDTO(usr.id, " +
            " usr.email, usr.phoneNumber, bk.id, bk.amount, bk.bookingStatus, bk.createdAt, bk.updatedAt) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "JOIN bk.user AS usr " +
            "WHERE bk.user.id = usr.id " +
            "AND bk.flightSchedule.id = fs.id " +
            "AND bk.bookingStatus = :booking_status")
    Page<BookingDTO> findBookingStatus(
            @Param("booking_status") BookingStatus bookingStatus,
            Pageable pageable);

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.InvoiceBookingDTO(bk.id, fs.id, " +
            "CONCAT(usr.firstName, ' ', usr.lastName), usr.phoneNumber, usr.email, " +
            "bk.bookingStatus, pm.name, bk.amount, CONCAT(fs.flightRoute.toAirport.name, ' - ', fs.flightRoute.fromAirport.name), " +
            "fs.aircraftDetail.aircraftClass, bk.createdAt) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "JOIN bk.user AS usr " +
            "JOIN bk.paymentMethod AS pm " +
            "WHERE bk.id = :booking_id " +
            "AND bk.user.id = :user_id")
    Optional<InvoiceBookingDTO> getInvoiceBooking(@Param("booking_id") String bookingId,
                                                  @Param("user_id") String userId);

    @Query(value = "SELECT bk FROM Booking AS bk " +
            "WHERE bk.id = :booking_id")
    Optional<Booking> checkStatus(
            @Param("booking_id") String bookingId);


    @Query(value = "SELECT new com.binar.flyket.dto.model.MyOrderDetailDTO(bk.id, fs.id, " +
            "CONCAT(usr.firstName, ' ', usr.lastName), usr.email, usr.phoneNumber, bk.bookingStatus, fs.aircraftDetail.aircraftClass, " +
            "pm.name, fs.flightRoute.fromAirport.name, fs.flightRoute.toAirport.name, " +
            "fs.departureTime, fs.arrivalTime, bk.amount) " +
            "FROM Booking AS bk " +
            "JOIN bk.user AS usr " +
            "JOIN bk.paymentMethod AS pm " +
            "JOIN bk.flightSchedule AS fs " +
            "WHERE bk.id =:booking_id")
    Optional<MyOrderDetailDTO> getMyOrderDetail(@Param("booking_id") String bookingId);

}
