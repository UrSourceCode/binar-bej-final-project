package com.binar.flyket.service;

import com.binar.flyket.dto.model.PaymentMethodDTO;
import com.binar.flyket.dto.request.PaymentMethodRequest;
import com.binar.flyket.dummy.PaymentDummies;
import com.binar.flyket.repository.PaymentMethodRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class PaymentMethodServiceImplTest {


    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @InjectMocks
    private PaymentMethodServiceImpl paymentMethodService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddPaymentMethod() {
        PaymentMethodRequest request = new PaymentMethodRequest();
        request.setName("INDOMARET");
        request.setId("payment-001");

        Mockito.when(paymentMethodRepository.findById(request.getId()))
                .thenReturn(Optional.empty());
        Mockito.when(paymentMethodRepository.save(PaymentDummies.paymentMethods().get(0)))
                .thenReturn(PaymentDummies.paymentMethods().get(0));

        var actualValue = paymentMethodService.addPaymentMethod(request);

        Assertions.assertEquals(true, actualValue);
    }

    @Test
    void testGetAll() {

        List<PaymentMethodDTO> expectedValue = PaymentDummies.paymentMethods()
                .stream().map(it -> {
                    PaymentMethodDTO pm = new PaymentMethodDTO();
                    pm.setName(it.getName());
                    pm.setId(it.getId());
                    return pm;
                }).toList();

        Mockito.when(paymentMethodRepository.findAll())
                .thenReturn(PaymentDummies.paymentMethods());

        var actualValue = paymentMethodService.getAll();

        Assertions.assertEquals(expectedValue.size(), actualValue.size());
        Assertions.assertEquals(expectedValue.get(0).getId(), actualValue.get(0).getId());
        Assertions.assertEquals(expectedValue.get(1).getId(), actualValue.get(1).getId());
    }

    @Test
    void testDeletePaymentMethod() {
        String pmId = "payment-001";

        Mockito.when(paymentMethodRepository.findById(pmId))
                .thenReturn(Optional.of(PaymentDummies.paymentMethods().get(0)));

        var actualValue = paymentMethodService.deletePaymentMethod(pmId);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);

    }

}