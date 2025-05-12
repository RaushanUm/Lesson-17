import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostmanEchoTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    public void testGetRequest() {
        Response response = given()
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")
                .when()
                .get("/get")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String argsFoo1 = jsonPath.getString("args.foo1");
        String argsFoo2 = jsonPath.getString("args.foo2");

        Assert.assertEquals(argsFoo1, "bar1", "args.foo1 должно быть 'bar1'");
        Assert.assertEquals(argsFoo2, "bar2", "args.foo2 должно быть 'bar2'");
    }

    @Test
    public void testPostRequestData() {
        String requestBody = "This is expected to be sent back as part of response body.";
        Response response = given()
                .contentType(ContentType.TEXT)
                .body(requestBody)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getString("data"), requestBody, "data должно соответствовать отправленному телу запроса");
    }

    @Test
    public void testPostRequestFormData() {
        String jsonBody = "{\"foo1\":\"bar1\",\"foo2\":\"bar2\"}";
        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("/post");

        response.then()
                .statusCode(200)
                .body("json.foo1", equalTo("bar1"))
                .body("json.foo2", equalTo("bar2"));
    }

    @Test
    public void testPutRequestRawData() {
        String requestBody = "This is expected to be sent back as part of response body.";
        Response response = given()
                .contentType(ContentType.TEXT)
                .body(requestBody)
                .when()
                .put("/put")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getString("data"), requestBody, "data должно соответствовать отправленному телу запроса");
    }

    @Test
    public void testPatchRequestRawData() {
        String requestBody = "This is expected to be sent back as part of response body.";
        Response response = given()
                .contentType(ContentType.TEXT)
                .body(requestBody)
                .when()
                .patch("/patch")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getString("data"), requestBody, "data должно соответствовать отправленному телу запроса");
    }

    @Test
    public void testDeleteRequestRawData() {
        String requestBody = "This is expected to be sent back as part of response body.";
        Response response = given()
                .contentType(ContentType.TEXT)
                .body(requestBody)
                .when()
                .delete("/delete")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getString("data"), requestBody, "data должно соответствовать отправленному телу запроса");
    }
}
