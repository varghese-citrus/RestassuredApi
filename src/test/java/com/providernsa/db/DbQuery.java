package com.providernsa.db;

public class DbQuery {
	public static final String GET_TOKEN="select TOP 1 APIToken from mtr.ClientAuthtoken where ClientId=10 order by AuthTokenId desc";
	public static final String GET_EXPIRES_ON="select TOP 1 ExpiredOn from mtr.ClientAuthtoken where ClientId=10 order by AuthTokenId desc";
	public static String GET_CLIENT_ID="select ClientId from mtr.Client where ParentId=10";
}
