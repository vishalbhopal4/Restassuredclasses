package com.sample.ActiveMQ;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class GetCustomerDetails {
	
	
	public RequestSpecification httpRequest;
	public static int RESPONSE200 = 200;
	public static int RESPONSE404 = 404;
	
	@BeforeMethod
	public void init() {
		RestAssured.baseURI = "http://localhost:4547/Blog.Api";
		httpRequest = RestAssured.given();		
	}

	//TC1 Description : Fetch value for customer id using get requestTC1
	
	@Test
	public void GetCustomerFound_positive() {
		
		String customerID = "1111";
		
		Response response = httpRequest.get("/"+ customerID+"/CustomerView");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode ,RESPONSE200 , "Correct status code returned");
			
		JsonPath jsonPathEvaluator = response.jsonPath();

		String status = jsonPathEvaluator.get("status");
		System.out.println("Status received from Response " + status);
		Assert.assertEquals(status, "success", "Correct status is not received.");
		
		int code = jsonPathEvaluator.get("code");
		System.out.println("Code received from Response : -> " + code);
		Assert.assertEquals(code, 0, "Correct status is not received.");
		
		String message = jsonPathEvaluator.get("message");
		System.out.println("message received from Response : ->  " + message);
		Assert.assertEquals(message, "", "Correct message not  received.");
		
		String cusId = jsonPathEvaluator.getString("data.customerID");
		System.out.println("cusId received from Response : -> " + cusId);
		Assert.assertEquals(cusId, customerID, "Correct cusId not received.");	
		
		String email = jsonPathEvaluator.get("data.email");
		System.out.println("email received from Response : ->  " + email);
		Assert.assertEquals(email, "testerA@abc.com", "Correct email is not received.");	
		
		String firstName = jsonPathEvaluator.get("data.first_name");
		System.out.println("firstName received from Response : -> " + firstName);
		Assert.assertEquals(firstName, "testerBFirst" , "Correct firstName not received.");	
		
		String lastName = jsonPathEvaluator.get("data.last_name");
		System.out.println("lastName received from Response : -> " + lastName);
		Assert.assertEquals(lastName, "testerBLast", "Correct lastName is not received.");	

	}
	
	
	//TC2 Description : Fetch value for non existing customer id using get request
	@Test
	public void GetCustomerFound_notFound() {
		
		String customerID = "11";
		
		RestAssured.baseURI = "http://localhost:4547/Blog.Api";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/"+ customerID+"/CustomerView");		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode , RESPONSE404 , "Correct status code returned");
			
		JsonPath jsonPathEvaluator = response.jsonPath();

		String status = jsonPathEvaluator.get("status");
		System.out.println("Status received from Response " + status);
		Assert.assertEquals(status, "fail", "InCorrect status received.");
		
		int code = jsonPathEvaluator.get("code");
		System.out.println("Code received from Response " + code);
		Assert.assertEquals(code, 1, "InCorrect status received.");
		
		String message = jsonPathEvaluator.get("message");
		System.out.println("message received from Response " + message);
		Assert.assertEquals(message, "Customer details not found.", "Incorrect message received.");
	
	}
	
	
	

}