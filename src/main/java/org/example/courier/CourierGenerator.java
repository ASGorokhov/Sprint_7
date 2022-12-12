package org.example.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public Courier defaultCourierData() {
        return new Courier("Ivan", "Pa$$word", "Kruzenshtern");
    }

    public Courier randomCourierData() {
        return new Courier(RandomStringUtils.randomAlphanumeric(8), RandomStringUtils.randomAlphanumeric(8), RandomStringUtils.randomAlphanumeric(8));
    }


}
