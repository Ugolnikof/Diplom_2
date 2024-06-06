package order;

import configure.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderMethods {

    @Step("create Order")
    public ValidatableResponse createOrder(Order order, String token) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(EnvConfig.BASE_URL)
                .body(order)
                .when()
                .post(EnvConfig.ORDER_PATH)
                .then().log().all();
    }

    @Step("create Order")
    public ValidatableResponse createOrder(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(order)
                .when()
                .post(EnvConfig.ORDER_PATH)
                .then().log().all();
    }

    @Step("get Order")
    public ValidatableResponse getOrder(String token) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(EnvConfig.BASE_URL)
                .when()
                .get(EnvConfig.ORDER_PATH)
                .then().log().all();
    }

    @Step("get Order without token")
    public ValidatableResponse getOrderWithoutToken() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .when()
                .get(EnvConfig.ORDER_PATH)
                .then().log().all();
    }
}
