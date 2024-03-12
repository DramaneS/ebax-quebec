package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Project getProjectSample1() {
        return new Project()
            .id(1L)
            .nameBuilding("nameBuilding1")
            .fullAddressBuilding("fullAddressBuilding1")
            .numberHousingUnits("numberHousingUnits1")
            .electricitySupplier("electricitySupplier1")
            .hydroQuebecContractNumber("hydroQuebecContractNumber1")
            .hydroQuebecMeterNumber("hydroQuebecMeterNumber1")
            .specifyMeteIntended("specifyMeteIntended1")
            .hydroQuebecAccountNumber("hydroQuebecAccountNumber1")
            .fileNumber("fileNumber1")
            .typeBuillding("typeBuillding1")
            .natureProject("natureProject1");
    }

    public static Project getProjectSample2() {
        return new Project()
            .id(2L)
            .nameBuilding("nameBuilding2")
            .fullAddressBuilding("fullAddressBuilding2")
            .numberHousingUnits("numberHousingUnits2")
            .electricitySupplier("electricitySupplier2")
            .hydroQuebecContractNumber("hydroQuebecContractNumber2")
            .hydroQuebecMeterNumber("hydroQuebecMeterNumber2")
            .specifyMeteIntended("specifyMeteIntended2")
            .hydroQuebecAccountNumber("hydroQuebecAccountNumber2")
            .fileNumber("fileNumber2")
            .typeBuillding("typeBuillding2")
            .natureProject("natureProject2");
    }

    public static Project getProjectRandomSampleGenerator() {
        return new Project()
            .id(longCount.incrementAndGet())
            .nameBuilding(UUID.randomUUID().toString())
            .fullAddressBuilding(UUID.randomUUID().toString())
            .numberHousingUnits(UUID.randomUUID().toString())
            .electricitySupplier(UUID.randomUUID().toString())
            .hydroQuebecContractNumber(UUID.randomUUID().toString())
            .hydroQuebecMeterNumber(UUID.randomUUID().toString())
            .specifyMeteIntended(UUID.randomUUID().toString())
            .hydroQuebecAccountNumber(UUID.randomUUID().toString())
            .fileNumber(UUID.randomUUID().toString())
            .typeBuillding(UUID.randomUUID().toString())
            .natureProject(UUID.randomUUID().toString());
    }
}
