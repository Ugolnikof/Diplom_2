package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

public class UserChecks {

    @Step("check User created successfully")
    public void createdSuccessfully(ValidatableResponse response, User user) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .and()
                .body("success", Matchers.equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.name", Matchers.equalTo(user.getName()))
                .body("user.email", Matchers.equalTo(user.getEmail()));
    }

    @Step("check User deleted successfully")
    public void deleteSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED)
                .and()
                .body("success", Matchers.equalTo(true));
    }

    @Step("check User twin created unsuccessfully")
    public void createdTwinUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("User already exists"));
    }

    @Step("check User with invalid field created unsuccessfully")
    public void createdInvalidUserUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Step("check User login successfully")
    public void loginSuccessfully(ValidatableResponse response, LoggedUser loggedUser) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", Matchers.equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.name", Matchers.equalTo(loggedUser.getName()))
                .body("user.email", Matchers.equalTo(loggedUser.getEmail()));
    }

    @Step("check User login with invalid field unsuccessfully")
    public void loginInvalidUserUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and()
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("email or password are incorrect"));
    }

    @Step("check User changed data successfully")
    public void changeSuccessfully(ValidatableResponse response, User user) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", Matchers.equalTo(true))
                .body("user.name", Matchers.equalTo(user.getName()))
                .body("user.email", Matchers.equalTo(user.getEmail()));
    }

    @Step("check User changed data unsuccessfully")
    public void changeUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and()
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("You should be authorised"));
    }


}
