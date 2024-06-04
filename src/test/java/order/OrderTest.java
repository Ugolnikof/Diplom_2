package order;

import ingredients.Ingredient;
import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("create new User")
    public void createUser() {
        user = User.getRandomUser();
        ValidatableResponse response = userProperties.createNewUser(user);
        token = User.getToken(response);
    }

    @Before
    @DisplayName("set Order")
    public void createOrder() {
        order = new Order();
        ingredients = new ArrayList<>();
        ingredients.add(Ingredient.getIngredients().get(0));
        ingredients.add(Ingredient.getIngredients().get(1));
        ingredients.add(Ingredient.getIngredients().get(2));
        order.setIngredients(ingredients);
    }

    @Test
    @DisplayName("create new Order with token")
    public void createOrderWithToken() {
        ValidatableResponse response = orderProperties.createOrder(order, token);
        orderChecks.createSuccessfully(response, user);
    }

    @Test
    @DisplayName("create new Order without token")
    public void createOrderWithoutToken() {
        ValidatableResponse response = orderProperties.createOrder(order);
        orderChecks.createWithoutTokenSuccessfully(response);
    }

    @Test
    @DisplayName("create new Order without ingredients")
    public void createOrderWithoutIngredients() {
        order.setIngredients(new ArrayList<>(){});
        ValidatableResponse response = orderProperties.createOrder(order);
        orderChecks.createWithoutIngredientsUnSuccessfully(response);
    }

    @Test
    @DisplayName("create new Order with invalid hash ingredients")
    public void createOrderWithInvalidHashIngredients() {
        ingredients.set(0, Ingredient.getIngredients().get(0) + "aaaaa");
        ingredients.set(1, Ingredient.getIngredients().get(1) + "aaaaa");
        ingredients.set(2, Ingredient.getIngredients().get(2) + "aaaaa");
        order.setIngredients(ingredients);
        ValidatableResponse response = orderProperties.createOrder(order);
        orderChecks.createWithInvalidHashIngredientsUnSuccessfully(response);
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
