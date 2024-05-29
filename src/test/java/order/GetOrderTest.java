package order;

import ingredients.Ingredient;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserChecks;
import user.UserProperties;

import java.util.ArrayList;

public class GetOrderTest {
    String token;
    User user;
    Order order;
    ArrayList<String> ingredients;
    private final UserProperties userProperties = new UserProperties();
    private final OrderProperties orderProperties = new OrderProperties();
    private final UserChecks userChecks = new UserChecks();
    private final OrderChecks orderChecks = new OrderChecks();

    @Before
    public void createUser() {
        user = User.getRandomUser();
        ValidatableResponse response = userProperties.createNewUser(user);
        token = User.getToken(response);
    }

    @Before
    public void createOrder() {
        order = new Order();
        ingredients = new ArrayList<>();
        ingredients.add(Ingredient.getIngredients().get(0));
        ingredients.add(Ingredient.getIngredients().get(1));
        ingredients.add(Ingredient.getIngredients().get(2));
        order.setIngredients(ingredients);
        orderProperties.createNewOrder(order, token);
    }

    @Test
    public void getOrderWithToken() {
        ValidatableResponse response = orderProperties.getOrder(token);
        orderChecks.getOrderSuccessfully(response);
    }

    @Test
    public void getOrderWithoutToken() {
        ValidatableResponse response = orderProperties.getOrderWithoutToken();
        orderChecks.getOrderWithoutTokenUnSuccessfully(response);
    }

    @After
    public void deleteUser() {
        if (token!= null) {
            ValidatableResponse response = userProperties.deleteExistingUser(token);
            userChecks.deleteSuccessfully(response);
        }
    }

}
