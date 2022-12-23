package com.binar.flyket.dto.mapper;

import com.binar.flyket.dto.model.TicketDTO;
import com.binar.flyket.model.Ticket;

public class TicketMapper {
    public static TicketDTO toDto(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setPassengerName(ticket.getPassengerName());
        ticketDTO.setPassengerTitle(ticket.getPassengerTitle());
        return ticketDTO;
    }
}
