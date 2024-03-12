package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EntrepriseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Entreprise getEntrepriseSample1() {
        return new Entreprise()
            .id(1L)
            .companyName("companyName1")
            .companyNeqNumber("companyNeqNumber1")
            .resourcePerson("resourcePerson1")
            .address("address1")
            .email("email1")
            .phone("phone1");
    }

    public static Entreprise getEntrepriseSample2() {
        return new Entreprise()
            .id(2L)
            .companyName("companyName2")
            .companyNeqNumber("companyNeqNumber2")
            .resourcePerson("resourcePerson2")
            .address("address2")
            .email("email2")
            .phone("phone2");
    }

    public static Entreprise getEntrepriseRandomSampleGenerator() {
        return new Entreprise()
            .id(longCount.incrementAndGet())
            .companyName(UUID.randomUUID().toString())
            .companyNeqNumber(UUID.randomUUID().toString())
            .resourcePerson(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString());
    }
}
