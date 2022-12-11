package com.binar.flyket.config;

import com.binar.flyket.model.Booking;
import com.binar.flyket.model.BookingStatus;
import com.binar.flyket.repository.BookingRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchedulerConfig {

    private BookingRepository bookingRepository;

    public SchedulerConfig(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Bean
    @Scheduled(cron = "0 */30 * * * *")
    public void checkStatusBooking() {
        System.out.println("~ Start Check Ticket ~");
        List<Booking> booking = bookingRepository.checkStatusBooking(System.currentTimeMillis(), BookingStatus.ACTIVE);
        for(int i = 0; i < booking.size(); i++) {
            booking.get(i).setBookingStatus(BookingStatus.EXPIRED);
        }
        bookingRepository.saveAll(booking);
        System.out.println("~ Finish ~");
    }
}
