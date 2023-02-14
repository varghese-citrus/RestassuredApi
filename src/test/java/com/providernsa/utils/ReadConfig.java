package com.providernsa.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {

	Properties pro;

	public ReadConfig() {
		try {
			FileInputStream fs = new FileInputStream("./configuration/config.properties");
			pro = new Properties();
			pro.load(fs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getConnectionUrl() {
		String url = pro.getProperty("connection_url");
		return url;
	}

	public String getDbUsername() {
		String dbUser = pro.getProperty("db_username");
		return dbUser;
	}

	public String getDbPassword() {
		String dbPass = pro.getProperty("db_password");
		return dbPass;
	}
	public String getBaseUri() {
		String uri = pro.getProperty("base_uri");
		return uri;
	}
	public String getUser() {
		String user = pro.getProperty("user");
		return user;
	}
	public String getPass() {
		String pass = pro.getProperty("pass");
		return pass;
	}
}
