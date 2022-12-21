package com.binar.flyket.repository;

import com.binar.flyket.dto.model.AvailableSeatDTO;
import com.binar.flyket.dto.model.PassengerTicketList;
import com.binar.flyket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    @Query(value = "SELECT new com.binar.flyket.dto.model.PassengerTicketList(tc.id, tc.passengerTitle, tc.passengerName, tc.seatDetail.id) " +
            "FROM Ticket AS tc WHERE tc.booking.id =:booking_id")
    List<PassengerTicketList> getRecentOrderDetail(@Param("booking_id") String bookingId);

    @Query(value = "SELECT new com.binar.flyket.dto.model.AvailableSeatDTO(sd.id) " +
            "FROM Ticket AS tc " +
            "JOIN tc.seatDetail AS sd " +
            "WHERE tc.flightSchedule.id = :schedule_id AND tc.status = true")
    List<AvailableSeatDTO> findAvailableSeat(@Param("schedule_id") String scheduleId);

    @Query(value = "SELECT tc FROM Ticket AS tc WHERE tc.booking.id = :booking_id")
    List<Ticket> findBooking(@Param("booking_id") String bookingId);

}
