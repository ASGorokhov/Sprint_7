package order;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.example.order.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetListTest {

    @Test
    @DisplayName("The query returns a list of orders")
    @Description("Тестируем, что запрос возвращает список заказов")
    public void queryReturnsAListOfOrders() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse responseOrderList = orderClient.getOrderList();
        responseOrderList.assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }

}
