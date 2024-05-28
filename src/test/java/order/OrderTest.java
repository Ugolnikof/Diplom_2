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


public class OrderTest {
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
    }

    @Test
    public void createOrderWithToken() {
        ValidatableResponse response = orderProperties.createNewOrder(order, token);
        orderChecks.createSuccessfully(response, user);
    }

    @Test
    public void createOrderWithoutToken() {
        ValidatableResponse response = orderProperties.createNewOrder(order);
        orderChecks.createWithoutTokenSuccessfully(response);
    }

    @Test
    public void createOrderWithoutIngredients() {
        order.setIngredients(new ArrayList<>(){});
        ValidatableResponse response = orderProperties.createNewOrder(order);
        orderChecks.createWithoutIngredientsUnSuccessfully(response);
    }

    @Test
    public void createOrderWithInvalidHashIngredients() {
        ingredients.set(0, Ingredient.getIngredients().get(0) + "aaaaa");
        ingredients.set(1, Ingredient.getIngredients().get(1) + "aaaaa");
        ingredients.set(2, Ingredient.getIngredients().get(2) + "aaaaa");
        order.setIngredients(ingredients);
        ValidatableResponse response = orderProperties.createNewOrder(order);
        orderChecks.createWithInvalidHashIngredientsUnSuccessfully(response);
    }

    @After
    public void deleteUser() {
        if (token!= null) {
            ValidatableResponse response = userProperties.deleteExistingUser(token);
            userChecks.deleteSuccessfully(response);
        }
    }


}
