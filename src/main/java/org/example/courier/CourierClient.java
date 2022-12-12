package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private static final String URL_COURIER_CREATE = "/api/v1/courier";
    public static final String URL_COURIER_LOGIN = "/api/v1/courier/login";
    public static final String URL_DELETE_COURIER = "/api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse creatingCourier(Courier courier) {
        return given().log().all()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(URL_COURIER_CREATE)
                .then().log().all();
    }
    @Step("Авторизация курьера")
    public ValidatableResponse courierLoggedIn(Credentials credentials) {
        return given().log().all()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(URL_COURIER_LOGIN)
                .then().log().all();
    };
    @Step("Удаление курьера по id")
    public ValidatableResponse deleteCourier(int courierId) {
       return given().log().all()
                .spec(getSpec())
                .when()
                .delete(URL_DELETE_COURIER + courierId)
               .then().log().all();
    }
}
