Feature: Validating place API's:
@AddPlace
Scenario Outline: Verify place is being successfully added with AddPlaceAPI
	Given : Add Place Payload with "<name>" "<language>" "<address>"
	When : user calls "AddPlaceAPI" with "post" http request
	Then : the API calls is success with status code 200
	And : "status" in response body is "OK"
	And : "scope" in response body is "APP"
	And : verify place_id created maps to "<name>" using "GetPlaceAPI"

Examples: 
	|name         | language  |address             |
	|Jypsy1| English   |world cross centerrr|

#@DeletePlace	
#Scenario: Verify if deletplace functionality  is working
#	Given : Delete place Payload
#	When : user calls "DeletePlaceAPI" with "POST" http request
#	Then : the API calls is success with status code 200
	
