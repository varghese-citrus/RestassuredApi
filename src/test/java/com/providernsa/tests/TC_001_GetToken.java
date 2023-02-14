package com.providernsa.tests;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.providernsa.api.EndPoints;
import com.providernsa.db.DbConnect;
import com.providernsa.db.DbQuery;
import com.providernsa.utils.ReadConfig;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

public class TC_001_GetToken extends DbConnect {

	TC_001_GetToken() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Test
	void getToken() throws SQLException {

		ReadConfig rc = new ReadConfig();
		String uri = rc.getBaseUri();
		String username = rc.getUser();
		String password = rc.getPass();
		RestAssured.baseURI = uri;
		httpRequest = RestAssured.given();
		JSONObject reqParams = new JSONObject();
		reqParams.put("userName", username);
		reqParams.put("password", password);
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(reqParams.toJSONString());
		response = httpRequest.request(Method.POST, EndPoints.GET_AUTH_TOKEN);
		JsonPath jsonPathEvaluator = response.jsonPath();
		String accessToken = jsonPathEvaluator.get("result.accessToken").toString();
		if (accessToken != null) {
			String query = DbQuery.GET_TOKEN;
			stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {
				String apiToken = resultSet.getString("APIToken");
				Assert.assertEquals(accessToken, apiToken);
			}
		} else {
			Assert.assertTrue(false);
		}
	}

}
