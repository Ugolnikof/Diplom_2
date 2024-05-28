package user;

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
    public void createUser() {
        user = User.getRandomUser();
        userProperties.createNewUser(user);
    }

    @Test
    public void loginUser() {
        LoggedUser loggedUser = LoggedUser.from(user);

        ValidatableResponse response = userProperties.loginNewUser(loggedUser);
        token = userChecks.loginSuccessfully(response, loggedUser);
    }

    @Test
    public void loginInvalidUser() {
        LoggedUser loggedUser = LoggedUser.from(user);
        String invalidPassword = loggedUser.getPassword().toUpperCase();
        loggedUser.setPassword(invalidPassword);

        ValidatableResponse response = userProperties.loginNewUser(loggedUser);
        userChecks.loginInvalidUserUnSuccessfully(response);
    }

    @After
    public void deleteUser() {
        if (token != null) {
            ValidatableResponse response = userProperties.deleteExistingUser(token);
            userChecks.deleteSuccessfully(response);
        }
    }


}
