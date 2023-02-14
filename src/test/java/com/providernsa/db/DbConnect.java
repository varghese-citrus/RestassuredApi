package com.providernsa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mysql.cj.jdbc.CallableStatement;
import com.providernsa.api.BaseClass;
import com.providernsa.utils.ReadConfig;

public class DbConnect extends BaseClass {
	public DbConnect(){
		super();
	}
	public static Connection connection = null;
	public static Statement stmt = null;
	

	ReadConfig rc = new ReadConfig();
	String url = rc.getConnectionUrl();
	String user = rc.getDbUsername();
	String pass = rc.getDbPassword();

	@BeforeClass
	public void dbSetup() {
		try {
			connection = DriverManager.getConnection(url,user,pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@AfterClass
	public void tearDown() throws SQLException {
		if(connection!=null) {
			connection.close();
		}
	}
}
