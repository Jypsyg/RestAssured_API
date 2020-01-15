package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.Static;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class stepDefination extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	JsonPath path ;
	static String place_id;

	@Given(": Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name , String language, String address) throws IOException {
		res = given().spec(requestSpecifications()).body(data.addPlacePayLoad(name,language,address));
	}
	
	@When(": user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		APIResources resourceAPI = APIResources.valueOf(resource);		
		System.out.println(resourceAPI.getResource());
		if (method.equalsIgnoreCase("POST")) 
			response = res.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET")) {
			response = res.when().get(resourceAPI.getResource());
			
		}		
	}

	@Then(": the API calls is success with status code {int}")
	public void the_API_calls_is_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);
	}

	@And(": {string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {

//		 path = new JsonPath(response.getBody().asString());
//		String actual_status = path.getString(keyValue);
		
		assertEquals(getJsonPath(response, keyValue),Expectedvalue);

	}
	
	@Then(": verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		//prep req spec
		//get API call
		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecifications()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource,"GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(expectedName,actualName);

	}

	@Given(": Delete place Payload")
	public void delete_place_Payload() throws IOException {
		res=  given().spec(requestSpecifications()).body(data.deletePlacePayLoad(place_id));
	}
	

}
