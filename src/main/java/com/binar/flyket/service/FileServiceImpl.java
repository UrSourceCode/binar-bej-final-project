package com.binar.flyket.service;

import com.binar.flyket.dto.model.FileDB;
import com.binar.flyket.dto.model.InvoiceBookingDTO;
import com.binar.flyket.dto.request.InvoiceRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Booking;
import com.binar.flyket.model.user.User;
import com.binar.flyket.repository.BookingRepository;
import com.binar.flyket.repository.UserRepository;
import com.binar.flyket.utils.Constants;
import com.binar.flyket.utils.JasperUtil;
import com.binar.flyket.utils.QRGenerator;
import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private JasperUtil jasperUtil;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;

    public FileServiceImpl(JasperUtil jasperUtil, BookingRepository bookingRepository, UserRepository userRepository) {
        this.jasperUtil = jasperUtil;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FileDB getInvoice(InvoiceRequest request) throws IOException, WriterException, JRException {
        Optional<User> user = userRepository.findById(request.getUserId());
        if(user.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "User " + Constants.NOT_FOUND_MSG);

        Optional<Booking> booking = bookingRepository.findById(request.getBookingId());
        if(booking.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Booking " + Constants.NOT_FOUND_MSG);

        if(booking.get().getPaymentMethod() == null)
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Payment Method " + Constants.NOT_FOUND_MSG);

        Optional<InvoiceBookingDTO> invoiceBooking = bookingRepository.getInvoiceBooking(request.getBookingId(), request.getUserId());
        String invoiceId = UUID.randomUUID().toString();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("date", new Date());
        parameters.put("invoice_id", invoiceId);
        parameters.put("order_id", invoiceBooking.get().getOrderId());
        parameters.put("cst_name", invoiceBooking.get().getCstName());
        parameters.put("flight_id", invoiceBooking.get().getFlightId());
        parameters.put("airport", invoiceBooking.get().getAirportName());
        parameters.put("phone_number", invoiceBooking.get().getPhoneNumber());
        parameters.put("email", invoiceBooking.get().getEmail());
        parameters.put("payment_name", invoiceBooking.get().getPaymentName());
        parameters.put("total_payment", "Rp."+invoiceBooking.get().getAmount().toString()+",-");
        parameters.put("status", invoiceBooking.get().getStatus().toString());
        parameters.put("QR", new ByteArrayInputStream(QRGenerator.getQRCodeImage(
                "https://api-flyket.up.railway.app/api/booking/check-booking-status?booking-id="+request.getBookingId(), 78, 80)));

        JasperReport jasperReport = jasperUtil.setJasperReport("/jasper/invoice.jrxml");
        JasperPrint jasperPrint = jasperUtil.setJasperPrint(jasperReport, parameters);

        FileDB fileDB = new FileDB();
        fileDB.setData(jasperUtil.toPdf(jasperPrint));
        fileDB.setFilename(invoiceBooking.get().getCstName() + "-" + invoiceId);
        return fileDB;
    }
}
