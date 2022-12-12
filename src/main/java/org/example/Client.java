package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Client {

    public static final String BASE_URI = "http://qa-scooter.praktikum-services.ru/";

    public RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .build();
    }
}
