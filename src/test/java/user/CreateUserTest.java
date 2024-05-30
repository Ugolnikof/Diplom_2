package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CreateUserTest {
    String token;
    User user;
    private final UserProperties userProperties = new UserProperties();
    private final UserChecks userChecks = new UserChecks();

    @Test
    @DisplayName("create new User")
    public void createUser() {
        user = User.getRandomUser();

        ValidatableResponse response = userProperties.createNewUser(user);
        token = User.getToken(response);
        userChecks.createdSuccessfully(response, user);
    }

    @Test
    @DisplayName("create User twin")
    public void createUserTwin() {
        user = User.getRandomUser();
        userProperties.createNewUser(user);

        User userTwin = User.getUserTwin(user);
        ValidatableResponse response = userProperties.createNewUser(userTwin);
        userChecks.createdTwinUnSuccessfully(response);
    }

    @Test
    @DisplayName("create User with invalid email")
    public void createInvalidUser() {
        user = User.getRandomUser();
        User userInvalid = User.getUserTwin(user);
        userInvalid.setEmail(null);

        ValidatableResponse response = userProperties.createNewUser(userInvalid);
        userChecks.createdInvalidUserUnSuccessfully(response);
    }

    @After
    @DisplayName("delete User")
    public void deleteUser() {
        if (token!= null) {
            ValidatableResponse response = userProperties.deleteExistingUser(token);
            userChecks.deleteSuccessfully(response);
        }
    }


}