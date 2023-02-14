package com.providernsa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.providernsa.api.EndPoints;
import com.providernsa.api.TokenManager;
import com.providernsa.db.DbConnect;
import com.providernsa.utils.ReadConfig;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC_002_VerifyToken extends DbConnect {

	TC_002_VerifyToken() {
		super();
	}

	@Test
	void tokenExpiry() {
		ReadConfig rc = new ReadConfig();
		String uri = rc.getBaseUri();

		String token = TokenManager.getToken();
		RestAssured.baseURI = uri;
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.queryParam("token", token);
		response = httpRequest.request(Method.POST, EndPoints.VALIDATE_TOKEN);
		Assert.assertEquals(response.statusCode(), 200);
	}
}
