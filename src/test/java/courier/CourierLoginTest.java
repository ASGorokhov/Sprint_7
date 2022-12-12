package courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.courier.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierLoginTest {
    private CourierGenerator courierGenerator = new CourierGenerator();
    private Credentials credentials;
    private CourierClient courierClient;
    private Courier courier;
    CourierAssertions courierAssertions;
    int idCourier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = courierGenerator.randomCourierData();
        courierClient.creatingCourier(courier);
        credentials = Credentials.from(courier);
        courierAssertions = new CourierAssertions();
    }

    @Test
    @DisplayName("Successful authorization of the courier returns its id")
    @Description("Тестируем, что при успешной авторизации курьера запрос возвращает его id")
    public void courierAuthorizationReturnsId() {
        ValidatableResponse responseLoginCourier = courierClient.courierLoggedIn(credentials);
        courierAssertions.courierLoggedInSuccessfully(responseLoginCourier);
        idCourier = responseLoginCourier.extract().path("id");
    }

    @Test
    @DisplayName("If you do not specify a login when authorizing the courier, the request will return an error")
    @Description("Тестируем, что если не указать логин при авторизации курьера, запрос вернет ошибку")
    public void courierAuthorizationWithoutLoginWillReturnAnError() {
        Credentials credentialsWithoutLogin = new Credentials("", courier.getPassword()); // c null тесты виснут
        ValidatableResponse responseLoginErrorMessage = courierClient.courierLoggedIn(credentialsWithoutLogin).statusCode(400);
        responseLoginErrorMessage.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("If you do not specify a password when authorizing the courier, the request will return an error")
    @Description("Тестируем, что если не указать пароль при авторизации курьера, запрос вернет ошибку")
    public void courierAuthorizationWithoutPasswordWillReturnAnError() {
        Credentials credentialsWithoutLogin = new Credentials(courier.getLogin(), "");
        ValidatableResponse responsePasswordErrorMessage = courierClient.courierLoggedIn(credentialsWithoutLogin).statusCode(400);
        responsePasswordErrorMessage.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("If you do not specify the login and password when authorizing the courier, the request will return an error")
    @Description("Тестируем, что если не указать логин и пароль при авторизации курьера, запрос вернет ошибку")
    public void courierAuthorizationWithoutLoginAndPasswordWillReturnAnError() {
        Credentials credentialsWithoutLoginAndPassword = new Credentials("", "");
        ValidatableResponse responseWithoutLoginAndPasswordMessage = courierClient.courierLoggedIn(credentialsWithoutLoginAndPassword).statusCode(400);
        responseWithoutLoginAndPasswordMessage.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Authorization under a non-existent user will return an error")
    @Description("Тестируем, что при попытке авторизации под несуществующим пользователем, запрос вернет ошибку")
    public void authorizationOfTheCourierUnderANonExistentUserWillReturnAnError() {
        Credentials credentialsWithNotExistingLogin = new Credentials(RandomStringUtils.randomAlphanumeric(6), courier.getPassword());
        ValidatableResponse responseWithWithNotExistingLoginMessage = courierClient.courierLoggedIn(credentialsWithNotExistingLogin).statusCode(404);
        responseWithWithNotExistingLoginMessage.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (idCourier != 0) {
            courierClient.deleteCourier(idCourier);
        }
    }
}
