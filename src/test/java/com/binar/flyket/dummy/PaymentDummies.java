package com.binar.flyket.dummy;

import com.binar.flyket.model.PaymentMethod;

import java.util.List;

public class PaymentDummies {

    public static List<PaymentMethod> paymentMethods() {
        PaymentMethod pm1 = new PaymentMethod();
        pm1.setId("payment-001");
        pm1.setName("INDOMARET");

        PaymentMethod pm2 = new PaymentMethod();
        pm2.setId("payment-002");
        pm2.setName("ALFAMART");

        return List.of(pm1, pm2);
    }
}
