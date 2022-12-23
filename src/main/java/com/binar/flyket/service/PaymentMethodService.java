package com.binar.flyket.service;

import com.binar.flyket.dto.model.PaymentMethodDTO;
import com.binar.flyket.dto.request.PaymentMethodRequest;

import java.util.List;

public interface PaymentMethodService {

    Boolean addPaymentMethod(PaymentMethodRequest request);

    List<PaymentMethodDTO> getAll();

    Boolean deletePaymentMethod(String id);

}
