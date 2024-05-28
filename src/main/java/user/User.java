package user;

import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    private String password;
    private String name;


    public static User getRandomUser() {
        Faker faker = new Faker();
        return User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
    }

    public static User getUserTwin(User user) {
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .build();
    }

    public static String getToken(ValidatableResponse response) {
        return response
                .extract()
                .path("accessToken");
    }
}
