package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InvoiceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Invoice getInvoiceSample1() {
        return new Invoice().id(1L).nameCustumer("nameCustumer1").adress("adress1").phone("phone1").email("email1");
    }

    public static Invoice getInvoiceSample2() {
        return new Invoice().id(2L).nameCustumer("nameCustumer2").adress("adress2").phone("phone2").email("email2");
    }

    public static Invoice getInvoiceRandomSampleGenerator() {
        return new Invoice()
            .id(longCount.incrementAndGet())
            .nameCustumer(UUID.randomUUID().toString())
            .adress(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
