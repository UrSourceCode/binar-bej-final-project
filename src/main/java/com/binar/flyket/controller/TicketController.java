package com.binar.flyket.controller;

import com.binar.flyket.dto.model.TicketDTO;
import com.binar.flyket.dto.request.TicketRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.TicketService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Tag(name = "Ticket")
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

//    @PostMapping("/add")
    public ResponseEntity<?> addTicket(@RequestBody TicketRequest ticketRequest) {
        try {
            ticketService.addTicket(ticketRequest);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, null));
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

//    @PostMapping("/add-all")
    public ResponseEntity<?> addTickets(@RequestBody List<TicketRequest> ticketRequest) {
        try {
            List<TicketDTO> ticketDTOS = ticketRequest.stream().map(this::ticketRequestToDto).toList();
            ticketService.addTickets(ticketDTOS);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSeat(@RequestParam("id") String ticketId, @RequestBody TicketRequest updateTicketRequest) {
        try {
            ticketService.updateTicket(ticketId, ticketRequestToDto(updateTicketRequest));
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

//    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTicketById(@RequestParam("/id") String ticketId) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, ticketService.deleteTicket(ticketId)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

    private TicketDTO ticketRequestToDto(TicketRequest request) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(request.getTicketId());
        ticketDTO.setPassengerName(request.getPassengerName());
        ticketDTO.setPassengerTitle(request.getPassengerTitle());
        return ticketDTO;
    }
}
