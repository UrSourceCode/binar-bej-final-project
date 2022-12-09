package com.binar.flyket.service;

import com.binar.flyket.dto.model.TicketDTO;
import com.binar.flyket.dto.request.TicketRequest;

import java.util.List;

public interface TicketService {
    Boolean addTicket(TicketRequest request);
    List<TicketDTO> addTickets (List<TicketDTO> ticketDTO);
    TicketDTO getTicketById (String ticketID);
    TicketDTO updateTicket (String ticketID, TicketDTO ticketDTO);
    TicketDTO deleteTicket (String ticketID);
}
