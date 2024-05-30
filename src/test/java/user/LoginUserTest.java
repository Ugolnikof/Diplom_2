package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {
    String token;
    User user;
    private final UserProperties userProperties = new UserProperties();
    private final UserChecks userChecks = new UserChecks();

    @Before
    @DisplayName("create new User")
    public void createUser() {
        user = User.getRandomUser();
        ValidatableResponse response = userProperties.createNewUser(user);
        token = User.getToken(response);
    }

    @Test
    @DisplayName("login this User")
    public void loginUser() {
        LoggedUser loggedUser = LoggedUser.from(user);

        ValidatableResponse response = userProperties.loginNewUser(loggedUser);
        userChecks.loginSuccessfully(response, loggedUser);
    }

    @Test
    @DisplayName("login this User with invalid password")
    public void loginInvalidUser() {
        LoggedUser loggedUser = LoggedUser.from(user);
        String invalidPassword = loggedUser.getPassword().toUpperCase();
        loggedUser.setPassword(invalidPassword);

        ValidatableResponse response = userProperties.loginNewUser(loggedUser);
        userChecks.loginInvalidUserUnSuccessfully(response);
    }

    @After
    @DisplayName("delete User")
    public void deleteUser() {
        if (token != null) {
            ValidatableResponse response = userProperties.deleteExistingUser(token);
            userChecks.deleteSuccessfully(response);
        }
    }


}
