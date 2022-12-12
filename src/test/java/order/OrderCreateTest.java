package order;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.example.order.Order;
import org.example.order.OrderClient;

import static org.hamcrest.CoreMatchers.*;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private OrderClient orderClient;
    private final String[] color;
    int track;

    public OrderCreateTest(String[] color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] getData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Create parameterized order data color and response body contains track")
    @Description("Тестируем, что при создании заказа можно указать один из цветов, оба цвета или не указывать цвет и что, тело ответа содержит track")
    public void createOrderParameterizedDataColorAndResponseBodyContainsTrack() {
        Order order = new Order(color);
        ValidatableResponse responseCreateOrder = orderClient.createAnOrder(order);
        track = responseCreateOrder.extract().path("track");
        responseCreateOrder.assertThat()
                .statusCode(201)
                .body("track", is(notNullValue()));
    }

    @After
    @Step("Удаление заказа")
    public void deleteOrder() {
            orderClient.deleteOrder(track);
        }
    }


