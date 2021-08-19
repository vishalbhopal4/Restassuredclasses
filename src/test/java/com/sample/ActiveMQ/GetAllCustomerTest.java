package com.sample.ActiveMQ;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetAllCustomerTest {

	@Test
	public void GetCustomerFound_positive() {

		RestAssured.baseURI = "http://localhost:4547/Blog.Api";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Customers");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode ,404 , "Correct status code returned");

		//calculate size of response array body.

		int size = response.jsonPath().getList("data").size();
		System.out.println("Available number of customers : ->  " + size);
		Assert.assertEquals(size ,6,"size is not as expected"); 
		List<Map<String, String>> responseMap = response.jsonPath().getList("data");
		System.out.println("List of All Customers available  -- ");
		for(Map<String,String> map : responseMap ) {
			for(Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String val ="";
				if(key.equals("id")) {
					val = String.valueOf(entry.getValue());
				}
				else{
					val = entry.getValue();
				}
				System.out.println(key + " : " + val);

			}
			System.out.println();
		}


	}


}
