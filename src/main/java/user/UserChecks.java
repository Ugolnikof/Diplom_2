package user;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

public class UserChecks {
    public String createdSuccessfully(ValidatableResponse response, User user) {
        return response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .and()
                .body("success", Matchers.equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.name", Matchers.equalTo(user.getName()))
                .body("user.email", Matchers.equalTo(user.getEmail()))
                .extract()
                .path("accessToken");
    }

    public void deleteSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED)
                .and()
                .body("success", Matchers.equalTo(true));
    }

    public void createdTwinUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("User already exists"));
    }

    public void createdInvalidUserUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    public String loginSuccessfully(ValidatableResponse response, LoggedUser loggedUser) {
        return response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", Matchers.equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.name", Matchers.equalTo(loggedUser.getName()))
                .body("user.email", Matchers.equalTo(loggedUser.getEmail()))
                .extract()
                .path("accessToken");
    }

    public void loginInvalidUserUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and()
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("email or password are incorrect"));
    }

    public void changeSuccessfully(ValidatableResponse response, User user) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", Matchers.equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.name", Matchers.equalTo(user.getName()))
                .body("user.email", Matchers.equalTo(user.getEmail()));
    }

    public void changeUnSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and()
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("You should be authorised"));
    }
}
