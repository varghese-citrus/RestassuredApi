package com.providernsa.tests;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.providernsa.api.EndPoints;
import com.providernsa.api.TokenManager;
import com.providernsa.db.DbConnect;
import com.providernsa.db.DbQuery;
import com.providernsa.utils.ReadConfig;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC_003_ValidateTokenWithClientAndParent extends DbConnect {

	TC_003_ValidateTokenWithClientAndParent() {
		super();
	}

	ReadConfig rc = new ReadConfig();
	String uri = rc.getBaseUri();
	

	@Test
	public void validateTokenWithClient() throws UnsupportedEncodingException {

		String token = TokenManager.getToken();
		int client_id = 10;
		RestAssured.baseURI = uri;
		httpRequest = RestAssured.given();
		httpRequest.header("accept", "text/plain");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.queryParam("token", token);
		httpRequest.queryParam("ClientId", client_id);
		response = httpRequest.request(Method.POST, EndPoints.VALIDATE_TOKEN_WITH_CLIENT_PARENT);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test
	public void validateTokenWithChild() throws SQLException {
		String token = TokenManager.getToken();
		ResultSet resultSet;
		ArrayList<String> list = new ArrayList<>();
		String query = DbQuery.GET_CLIENT_ID;
		stmt = connection.createStatement();
		resultSet = stmt.executeQuery(query);
		while (resultSet.next()) {
			resultSet.getString("ClientId");
			list.add(resultSet.getString("ClientId"));
		}
		for (int i = 0; i < list.size(); i++) {
			int client_id = Integer.parseInt(list.get(i));
			RestAssured.baseURI = uri;
			httpRequest = RestAssured.given();
			httpRequest.header("accept", "text/plain");
			httpRequest.header("Content-Type", "application/json");
			httpRequest.queryParam("token", token);
			httpRequest.queryParam("ClientId", client_id);
			response = httpRequest.request(Method.POST, EndPoints.VALIDATE_TOKEN_WITH_CLIENT_PARENT);
			Assert.assertEquals(response.getStatusCode(), 200);
		}
	}

}
