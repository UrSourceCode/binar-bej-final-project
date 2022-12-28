package com.binar.flyket.service;

import com.binar.flyket.dto.mapper.TicketMapper;
import com.binar.flyket.dto.model.TicketDTO;
import com.binar.flyket.dto.request.TicketRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Ticket;
import com.binar.flyket.repository.TicketRepository;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Boolean addTicket(TicketRequest request) {
        Optional<Ticket> ticket = ticketRepository.findById(request.getTicketId());
        if (ticket.isEmpty()) {
            Ticket ticketModel = new Ticket();
            ticketModel.setId(request.getTicketId());
            ticketRepository.save(ticketModel);
            return true;
        }
        throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.CONFLICT, Constants.ALREADY_EXIST_MSG);
    }

    @Override
    public List<TicketDTO> addTickets(List<TicketDTO> ticketDTO) {
        List<Ticket> tickets = ticketDTO.stream()
                .map(ticket -> {
                    Ticket ticketModel = new Ticket();
                    ticketModel.setId(ticket.getId());
                    ticketModel.setPassengerName(ticket.getPassengerName());
                    ticketModel.setPassengerTitle(ticket.getPassengerTitle());
                    return ticketModel;
                }).toList();
        ticketRepository.saveAll(tickets);
        return ticketDTO;
    }

    @Override
    public TicketDTO getTicketById(String ticketID) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketID);
        if (ticket.isPresent())
            return TicketMapper.toDto(ticket.get());
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.TICKET_NOT_FOUND);
    }

    @Override
    public TicketDTO updateTicket(String ticketID, TicketDTO ticketDTO) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketID);
        if (ticket.isPresent()) {
            Ticket ticketModel = new Ticket();
            ticketModel.setId(ticketID);
            ticketModel.setPassengerName(ticketDTO.getPassengerName());
            ticketModel.setPassengerTitle(ticketDTO.getPassengerTitle());
            return TicketMapper.toDto(ticketModel);
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.TICKET_NOT_FOUND);
    }

    @Override
    public TicketDTO deleteTicket(String ticketID) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketID);
        if (ticket.isPresent()) {
            ticketRepository.delete(ticket.get());
            return TicketMapper.toDto(ticket.get());
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.TICKET_NOT_FOUND);
    }
}
