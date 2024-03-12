package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Service getServiceSample1() {
        return new Service().id(1L).nameService("nameService1").companyName("companyName1").phone("phone1").email("email1");
    }

    public static Service getServiceSample2() {
        return new Service().id(2L).nameService("nameService2").companyName("companyName2").phone("phone2").email("email2");
    }

    public static Service getServiceRandomSampleGenerator() {
        return new Service()
            .id(longCount.incrementAndGet())
            .nameService(UUID.randomUUID().toString())
            .companyName(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
