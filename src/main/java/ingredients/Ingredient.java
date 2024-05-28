package ingredients;

import configure.EnvConfig;
import io.restassured.http.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

@Data
@AllArgsConstructor
public class Ingredient {
    public static ArrayList<String> getIngredients() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .when()
                .get(EnvConfig.INGREDIENTS_PATH)
                .then().log().all()
                .extract()
                .path("data._id");
    }
}
