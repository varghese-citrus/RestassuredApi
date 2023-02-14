package com.providernsa.api;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

import com.providernsa.db.DbConnect;
import com.providernsa.db.DbQuery;
import com.providernsa.utils.ReadConfig;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

public class TokenManager extends DbConnect {

	TokenManager() {
		super();
	}

	private static String access_token;
	private static long expire_time;
	private static String token_expires;

	public static String getToken() {

		try {

			String getExpires = DbQuery.GET_EXPIRES_ON;
			String getToken = DbQuery.GET_TOKEN;

			Statement stmt_1 = connection.createStatement();
			Statement stmt_2 = connection.createStatement();

			ResultSet rs_1 = stmt_1.executeQuery(getExpires);
			ResultSet rs_2 = stmt_2.executeQuery(getToken);

			while (rs_1.next() && rs_2.next()) {
				token_expires = rs_1.getString("ExpiredOn");
				access_token = rs_2.getString("APIToken");

			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date = format.parse(token_expires);
			expire_time = date.getTime();

			if (System.currentTimeMillis() > expire_time) {
				access_token = renewAccessToken();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Abort!failed to get token!");
		}
		return access_token;
	}

	@SuppressWarnings("unchecked")
	private static String renewAccessToken() throws ParseException {

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
		access_token = jsonPathEvaluator.get("result.accessToken").toString();

		return access_token;

	}

}
