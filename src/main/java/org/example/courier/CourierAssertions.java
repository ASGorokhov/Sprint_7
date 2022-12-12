package org.example.courier;

import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class CourierAssertions {
    public void creationOfCourierSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    public void creatingACourierWithMissingData(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    public void creatingACourierWithDuplicateData(ValidatableResponse response) {
        response.assertThat()
                .statusCode(409)
                .body("message" , equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    public void courierLoggedInSuccessfully(ValidatableResponse response) {
         response.assertThat()
                .statusCode(200)
                .body("id", greaterThan(0));
    }
}
