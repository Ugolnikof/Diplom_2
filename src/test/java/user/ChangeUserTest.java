package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChangeUserTest {
    String token;
    User user;
    private final UserMethods userMethods = new UserMethods();
    private final UserChecks userChecks = new UserChecks();

    @Before
    @DisplayName("create new User")
    public void createUser() {
        user = User.getRandomUser();
        ValidatableResponse response = userMethods.createNewUser(user);
        token = User.getToken(response);
    }

    @Test
    @DisplayName("change User data with token")
    public void changeUserWithToken() {
        user = User.getRandomUser();

        ValidatableResponse response = userMethods.changeUser(user, token);
        userChecks.changeSuccessfully(response, user);
    }

    @Test
    @DisplayName("change User data without token")
    public void changeUserWithoutToken() {
        user = User.getRandomUser();

        ValidatableResponse response = userMethods.changeUser(user);
        userChecks.changeUnSuccessfully(response);
    }

    @After
    @DisplayName("delete User")
    public void deleteUser() {
        if (token != null) {
            ValidatableResponse response = userMethods.deleteExistingUser(token);
            userChecks.deleteSuccessfully(response);
        }
    }

}
