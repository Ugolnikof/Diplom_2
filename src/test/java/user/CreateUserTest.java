package user;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CreateUserTest {
    String token;
    User user;
    private final UserProperties userProperties = new UserProperties();
    private final UserChecks userChecks = new UserChecks();

    @Test
    public void createUser() {
        user = User.getRandomUser();

        ValidatableResponse response = userProperties.createNewUser(user);
        token = userChecks.createdSuccessfully(response, user);
    }

    @Test
    public void createUserTwin() {
        user = User.getRandomUser();
        userProperties.createNewUser(user);

        User userTwin = User.getUserTwin(user);
        ValidatableResponse response = userProperties.createNewUser(userTwin);
        userChecks.createdTwinUnSuccessfully(response);
    }

    @Test
    public void createInvalidUser() {
        user = User.getRandomUser();
        User userInvalid = User.getUserTwin(user);
        userInvalid.setEmail(null);

        ValidatableResponse response = userProperties.createNewUser(userInvalid);
        userChecks.createdInvalidUserUnSuccessfully(response);
    }

    @After
    public void deleteUser() {
        if (token != null) {
            ValidatableResponse response = userProperties.deleteExistingUser(token);
            userChecks.deleteSuccessfully(response);
        }
    }


}