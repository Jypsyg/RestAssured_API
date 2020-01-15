package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		//execute only when placeid is null
		stepDefination m = new stepDefination();
		if (stepDefination.place_id==null) {
			m.add_Place_Payload_with("shetty", "french", "Indore");
			m.user_calls_with_http_request("AddPlaceAPI", "POST");
			m.verify_place_id_created_maps_to_using("shetty", "GetPlaceAPI");
		}
	
	}
}
