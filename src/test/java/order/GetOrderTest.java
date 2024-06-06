package order;

import ingredients.Ingredient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserChecks;
import user.UserMethods;

import java.util.ArrayList;

public class GetOrderTest {
    String token;
    User user;
    Order order;
    ArrayList<String> ingredients;
    private final UserMethods userMethods = new UserMethods();
    private final OrderMethods orderMethods = new OrderMethods();
    private final UserChecks userChecks = new UserChecks();
    private final OrderChecks orderChecks = new OrderChecks();

    @Before
    @DisplayName("create new User")
    public void createUser() {
        user = User.getRandomUser();
        ValidatableResponse response = userMethods.createNewUser(user);
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
        orderMethods.createOrder(order, token);
    }

    @Test
    @DisplayName("get Order with token")
    public void getOrderWithToken() {
        ValidatableResponse response = orderMethods.getOrder(token);
        orderChecks.getOrderSuccessfully(response);
    }

    @Test
    @DisplayName("get Order without token")
    public void getOrderWithoutToken() {
        ValidatableResponse response = orderMethods.getOrderWithoutToken();
        orderChecks.getOrderWithoutTokenUnSuccessfully(response);
    }

    @After
    @DisplayName("delete User")
    public void deleteUser() {
        if (token!= null) {
            ValidatableResponse response = userMethods.deleteExistingUser(token);
            userChecks.deleteSuccessfully(response);
        }
    }

}
