package restAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class PostMethod {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		//Creating a resource
		String response = given().log().all().header("Content-Type","application/json")
				.body("{\r\n" + 
						"    \"title\":\"foo\",\r\n" + 
						"    \"body\":\"bar\",\r\n" + 
						"    \"userId\":1\r\n" + 
						"}").when().post("posts")
				.then().log().all().assertThat().statusCode(201).body("id", equalTo(101))
				.header("Server", "cloudflare").extract().response().asString();
		JsonPath js = new JsonPath(response);
		String body = js.getString("body");
		//Updating the resource
		given().log().all().header("Content-Type","application/json")
		.body("{\r\n" + 
				"    \"title\": \"ghf\",\r\n" + 
				"   \"body\": \"" + body + "\",\r\n" + 
				"    \"userId\": 2\r\n" + 
				"}")
		.when().put("posts/1")
		.then().log().all().assertThat().statusCode(200);
		//Deleting the resource
		given().log().all().body("{\r\n" + 
				"    \"body\":\"bar\"\r\n" + 
				"}")
		.when().delete("posts/1")
		.then().log().all().statusCode(200);
		
	}

}
