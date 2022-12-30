package com.binar.flyket.dummy;

import com.binar.flyket.model.Ticket;

import java.util.List;

public class TicketDummy {
    public static List<Ticket> ticketList() {
        Ticket t1 = new Ticket();
        t1.setId("AXWSJ");
        t1.setPassengerTitle("Mrs.");
        t1.setPassengerName("Unit Testing");

        Ticket t2 = new Ticket();
        t2.setId("GHNYA");
        t2.setPassengerTitle("Mr.");
        t2.setPassengerName("Unit Testing");

        return List.of(t1, t2);
    }
}
