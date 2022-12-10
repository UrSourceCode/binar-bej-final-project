package com.binar.flyket.service;

import com.binar.flyket.dto.model.PaymentMethodDTO;
import com.binar.flyket.dto.request.PassengerRequest;
import com.binar.flyket.dto.request.PaymentMethodRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.PaymentMethod;
import com.binar.flyket.repository.PaymentMethodRepository;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public Boolean addPaymentMethod(PaymentMethodRequest request) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(request.getId());
        if(paymentMethod.isPresent()) throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.CONFLICT, Constants.ALREADY_EXIST_MSG);
        PaymentMethod paymentMethodModel = new PaymentMethod();
        paymentMethodModel.setId(request.getId());
        paymentMethodModel.setName(request.getName());
        return true;
    }

    @Override
    public List<PaymentMethodDTO> getAll() {
        return paymentMethodRepository.findAll().stream().map(it -> {
            PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
            paymentMethodDTO.setId(it.getId());
            paymentMethodDTO.setName(it.getName());
            return paymentMethodDTO;
        }).toList();
    }

    @Override
    public Boolean deletePaymentMethod(String id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id);
        if(paymentMethod.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
        paymentMethodRepository.delete(paymentMethod.get());
        return true;
    }
}
