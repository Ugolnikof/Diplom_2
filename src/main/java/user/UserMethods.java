package user;

import configure.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserMethods {

    @Step("create User")
    public ValidatableResponse createNewUser(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(user)
                .when()
                .post(EnvConfig.AUTH_PATH + "/register")
                .then().log().all();
    }

    @Step("delete User")
    public ValidatableResponse deleteExistingUser(String token) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(EnvConfig.BASE_URL)
                .when()
                .delete(EnvConfig.USER_PATH)
                .then().log().all();
    }

    @Step("login User")
    public ValidatableResponse loginNewUser(LoggedUser loggedUser) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(loggedUser)
                .when()
                .post(EnvConfig.AUTH_PATH + "/login")
                .then().log().all();
    }

    @Step("change User")
    public ValidatableResponse changeUser(User user, String token) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(EnvConfig.BASE_URL)
                .body(user)
                .when()
                .patch(EnvConfig.USER_PATH)
                .then().log().all();
    }

    @Step("change User")
    public ValidatableResponse changeUser(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(user)
                .when()
                .patch(EnvConfig.USER_PATH)
                .then().log().all();
    }


}
