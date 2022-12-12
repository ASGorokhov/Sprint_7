package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    public static final String URL_ORDER = "/api/v1/orders";
    public static final String URL_DELETE_ORDER = "/api/v1/orders/cancel";

    @Step("Создание заказа")
    public ValidatableResponse createAnOrder(Order order) {
        return given().log().all()
                .spec(getSpec())
                .body(order)
                .when()
                .post(URL_ORDER)
                .then().log().all();
    }

    @Step("Получить список заказов")
    public ValidatableResponse getOrderList() {
        return given().log().all()
                .spec(getSpec())
                .when()
                .get(URL_ORDER)
                .then().log().all();
    }

    @Step("Удаление созданного заказа")
    public ValidatableResponse deleteOrder(int track) {
        return given().log().all()
                .spec(getSpec())
                .body(track)
                .when()
                .put(URL_DELETE_ORDER)
                .then().log().all();
    }
}
