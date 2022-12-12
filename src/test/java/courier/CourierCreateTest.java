package courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.courier.*;

public class CourierCreateTest {
    private CourierGenerator courierGenerator = new CourierGenerator();
    private CourierClient courierClient;
    private Courier courier;
    private CourierAssertions courierAssertions;
    int idCourier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = courierGenerator.randomCourierData();
        courierAssertions = new CourierAssertions();
    }

    @Test
    @DisplayName("Successful courier creation returns ok: true")
    @Description("Тестируем, что при успешном создании курьера запрос возвращает ok: true")
    public void createCourierSuccessfulRequestReturnsOK() {
        ValidatableResponse responseCreateCourier = courierClient.creatingCourier(courier);
        courierAssertions.creationOfCourierSuccessfully(responseCreateCourier);
        Credentials credentials = Credentials.from(courier);
        ValidatableResponse responseLoginCourier = courierClient.courierLoggedIn(credentials);
        idCourier = responseLoginCourier.extract().path("id");
    }

    @Test
    @DisplayName("If you do not specify a login when creating a courier, the request returns an error")
    @Description("Тестируем, что если при создании курьера не указать логин, запрос возвращает ошибку")
    public void creatingACourierWithoutALoginRequestReturnsAnError() {
        courier.setLogin(null);
        ValidatableResponse responseNullLogin = courierClient.creatingCourier(courier);
        courierAssertions.creatingACourierWithMissingData(responseNullLogin);
    }

    @Test
    @DisplayName("If you do not specify a password when creating a courier, the request returns an error")
    @Description("Тестируем, что если при создании курьера не указать пароль, запрос возвращает ошибку")
    public void creatingACourierWithoutAPasswordReturnsAnError() {
        courier.setPassword(null);
        ValidatableResponse responseNullPassword = courierClient.creatingCourier(courier);
        courierAssertions.creatingACourierWithMissingData(responseNullPassword);
    }

    @Test
    @DisplayName("If you do not specify a login and password when creating a courier, the request returns an error")
    @Description("Тестируем, что если при создании курьера не указать логин и пароль, запрос возвращает ошибку")
    public void whenCreatingACourierWithoutALoginAndPasswordReturnsAnError() {
        courier.setLogin(null);
        courier.setPassword(null);
        ValidatableResponse responseNullFields = courierClient.creatingCourier(courier);
        courierAssertions.creatingACourierWithMissingData(responseNullFields);
    }

    @Test
    @DisplayName("Create a duplicate courier returns an error")
    @Description("Тестируем, что при попытке создать уже существующего курьера, запрос возвращает ошибку")
    public void createADuplicateCourierReturnsAnError() {
        courierClient.creatingCourier(courier);
        ValidatableResponse responseCreateCourier = courierClient.creatingCourier(courier);
        courierAssertions.creatingACourierWithDuplicateData(responseCreateCourier);
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (idCourier != 0) {
            courierClient.deleteCourier(idCourier);
        }
    }
}

