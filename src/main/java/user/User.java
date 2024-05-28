package user;

import com.github.javafaker.Faker;
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
        return new User(user.getEmail(), user.getPassword(), user.getName());
    }
}
