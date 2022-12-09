package com.binar.flyket.repository;

import com.binar.flyket.dto.model.AvailableSeatDTO;
import com.binar.flyket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    @Query(value = "SELECT tc FROM Ticket AS tc ")
   Optional<Ticket> findAvailableTicket(@Param("schedule_id") String scheduleId);

}
