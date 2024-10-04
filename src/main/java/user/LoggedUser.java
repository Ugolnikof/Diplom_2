package user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUser {
    private String email;
    private String password;
    private String name;

    public static LoggedUser from(User user) {
        return new LoggedUser(user.getEmail(), user.getPassword(), user.getName());
    }
}
