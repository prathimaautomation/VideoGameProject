package testcases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TC_VideoGamesAPI {


    @Test(priority = 1)
    public void test_getAllVideoGames(){
        given()
                .when()
                .get("http://localhost:8080/app/videogames")
                .then()
                .statusCode(200);
    }

    @Test(priority = 2)
    public void test_addNewVideoGame(){
        HashMap data=new HashMap();
        data.put("id", "104");
        data.put("name", "Pucman");
        data.put("releaseDate", "2021-02-27T09:55:58.510Z");
        data.put("reviewScore", "5");
        data.put("category", "Adventure");
        data.put("rating", "universal");

        Response res=given()
                .contentType("application/json")
                .body(data)
                .when()
                .post("http://localhost:8080/app/videogames")
                .then()
                .statusCode(200)
                .log().body()
                .extract().response();

        String jsonString=res.asString();

        Assert.assertTrue(jsonString.contains("Record Added Successfully"));
        //Assert.assertTrue(jsonString.contains("Record Added Successfully"));

    }

    @Test(priority = 3)
    public void test_getVideoGame(){
        given()
                .when()
                .get("http://localhost:8080/app/videogames/104")
                .then()
                .statusCode(200)
                .log().body()
        .body("VideoGame.id",equalTo("104"))
        .body("VideoGame.name", equalTo("Pucman"));
    }

    @Test(priority = 4)
    public void test_UpdateVideoGame(){
        HashMap data=new HashMap();
        data.put("id", "100");
        data.put("name", "Batman");
        data.put("releaseDate", "2021-02-27T09:00:58.510Z");
        data.put("reviewScore", "4");
        data.put("category", "Adventure");
        data.put("rating", "universal");

        given()
                .contentType("application/json")
                .body(data)
                .when()
                .put("http://localhost:8080/app/videogames/100")
                .then()
                .statusCode(200)
                .log().body()
                .body("VideoGame.id",equalTo("100"))
                .body("VideoGame.name", equalTo("Batman"));
    }

    @Test(priority=5)
    public void test_DeleteVideoGame() throws InterruptedException {
        Response res=given()
                .when()
                .delete("http://localhost:8080/app/videogames/104")
        .then()
        .statusCode(200)
        .log().body()
        .extract().response();

        Thread.sleep(3000);
        String jsonString=res.asString();

        Assert.assertTrue(jsonString.contains("Record Deleted Successfully"));
    }


}
