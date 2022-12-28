package com.binar.flyket.service;

import com.binar.flyket.dto.model.FileDB;
import com.binar.flyket.dto.request.InvoiceRequest;
import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

public interface FileService {

    FileDB getInvoice(InvoiceRequest request) throws IOException, WriterException, JRException;
}
