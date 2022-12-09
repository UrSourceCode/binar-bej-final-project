package com.binar.flyket.repository;

import com.binar.flyket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    @Query(value = "SELECT tc FROM Ticket tc WHERE tc.flightSchedule.id =:schedule_id AND tc.availableSeat.id =:avs_id")
    List<Ticket> findAllTicketByScheduleAndAvsId(@Param("schedule_id") String scheduleId,
                                         @Param("avs_id") Integer avsId);
}
