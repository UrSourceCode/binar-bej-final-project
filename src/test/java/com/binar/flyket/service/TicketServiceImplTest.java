package com.binar.flyket.service;

import com.binar.flyket.dto.mapper.TicketMapper;
import com.binar.flyket.dto.model.TicketDTO;
import com.binar.flyket.dto.request.TicketRequest;
import com.binar.flyket.dummy.TicketDummy;
import com.binar.flyket.model.Ticket;
import com.binar.flyket.repository.TicketRepository;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TicketServiceImplTest {
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddTicket() {
        TicketRequest request = new TicketRequest();
        request.setTicketId("AXWSJ");
        request.setPassengerTitle("Mrs.");
        request.setPassengerName("Unit Testing");

        Mockito.when(ticketRepository.findById(request.getTicketId())).thenReturn(Optional.empty());
        Mockito.when(ticketRepository.save(TicketDummy.ticketList().get(0))).thenReturn(TicketDummy.ticketList().get(0));

        var actualVal = ticketService.addTicket(request);
        var expectedVal = true;

        Assertions.assertEquals(expectedVal, actualVal);
    }

    @Test
    void addTickets() {
        TicketDTO request1 = new TicketDTO();
        request1.setId("GHNYA");
        request1.setPassengerTitle("Mr.");
        request1.setPassengerName("Unit Testing");

        TicketDTO request2 = new TicketDTO();
        request2.setId("AXWSJ");
        request2.setPassengerTitle("Mrs.");
        request2.setPassengerName("Unit Testing");

        List<TicketDTO> listRequest = new ArrayList<>();
        listRequest.add(request1);
        listRequest.add(request2);

        List<Ticket> listModel = listRequest.stream().map(ticketRequest -> {
            Ticket ticketModel = new Ticket();
            ticketModel.setId(ticketRequest.getId());
            ticketModel.setPassengerTitle(ticketRequest.getPassengerTitle());
            ticketModel.setPassengerName(ticketRequest.getPassengerName());
            return ticketModel;
        }).toList();

        Mockito.when(ticketRepository.saveAll(listModel)).thenReturn(listModel);

        var actualVal = ticketService.addTickets(listRequest);
        var expectedSize = 2;

        Assertions.assertEquals(expectedSize, actualVal.size());
    }

    @Test
    void getTicketById() {
        String id = "AXWSJ";

        Mockito.when(ticketRepository.findById(id)).thenReturn(Optional.of(TicketDummy.ticketList().get(0)));

        var actualVal = ticketService.getTicketById(id);
        var expectedId = "AXWSJ";
        var expectedPassengerName = "Unit Testing";
        var expectedPassengerTitle = "Mrs.";

        Assertions.assertEquals(expectedId, actualVal.getId());
        Assertions.assertEquals(expectedPassengerName, actualVal.getPassengerName());
        Assertions.assertEquals(expectedPassengerTitle, actualVal.getPassengerTitle());
    }

    @Test
    void updateTicket() {
        String id = "AXWSJ";
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setPassengerName("Unit Testing Update");
        ticketDTO.setPassengerTitle("Miss.");

        Mockito.when(ticketRepository.findById(id))
                .thenReturn(Optional.of(TicketDummy.ticketList().get(0)));

        var actualVal = ticketService.updateTicket(id, ticketDTO);
        var expectedName = "Unit Testing Update";
        var expectedTitle = "Miss.";

        Assertions.assertEquals(expectedName, actualVal.getPassengerName());
        Assertions.assertEquals(expectedTitle, actualVal.getPassengerTitle());
    }

    @Test
    void deleteTicket() {
        String id = "AXWSJ";

        Mockito.when(ticketRepository.findById(id))
                .thenReturn(Optional.of(TicketDummy.ticketList().get(0)));
        Mockito.doNothing()
                .when(ticketRepository).delete(TicketDummy.ticketList().get(0));

        var actualVal = ticketService.deleteTicket(id);
        var expectedId = "AXWSJ";
        var expectedPassengerName = "Unit Testing";
        var expectedPassengerTitle = "Mrs.";

        Assertions.assertEquals(expectedId, actualVal.getId());
        Assertions.assertEquals(expectedPassengerName, actualVal.getPassengerName());
        Assertions.assertEquals(expectedPassengerTitle, actualVal.getPassengerTitle());
    }
}