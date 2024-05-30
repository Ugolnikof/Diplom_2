package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import user.User;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderChecks {

    @Step("check Order created successfully")
    public void createSuccessfully(ValidatableResponse response, User user) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .and()
                .body("success", Matchers.equalTo(true))
                .body("name", notNullValue())
                .body("order.ingredients", notNullValue())
                .body("order._id", notNullValue())
                .body("order.owner.name", Matchers.equalTo(user.getName()))
                .body("order.owner.email", Matchers.equalTo(user.getEmail()))
                .body("order.status", Matchers.equalTo("done"))
                .body("order.number", notNullValue())
                .body("order.price", notNullValue());
    }

    @Step("check Order created without token successfully")
    public void createWithoutTokenSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .and()
                .body("success", Matchers.equalTo(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }

    @Step("check Order created without ingredients successfully")
    public void createWithoutIngredientsUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .and()
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Ingredient ids must be provided"));
    }

    @Step("check Order created with invalid hash ingredients unsuccessfully")
    public void createWithInvalidHashIngredientsUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND);
    }

    @Step("check Order got successfully")
    public void getOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", Matchers.equalTo(true))
                .body("orders", notNullValue());
    }

    @Step("check Order got without token unsuccessfully")
    public void getOrderWithoutTokenUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and()
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("You should be authorised"));
    }
}
