package com.mhc.util;

public class Constants {

	public final static int DOCUMENT_CHUNK_SIZE = (1024 * 1024) * 10;

	private static String CORESERVICES_PROPERTY_SALT = null;

	public static String getCORESERVICES_PROPERTY_SALT() {
		return CORESERVICES_PROPERTY_SALT;
	}

	public static void setCORESERVICES_PROPERTY_SALT(String salt) {
		CORESERVICES_PROPERTY_SALT = salt;
	}

	public static final String ERROR_SERVER = "error.2000";
	public static final String ERROR_INVALID_CLIENT_ID = "error.2001";
	public static final String ERROR_INVALID_IP = "error.2007";
	public static final String ERROR_INVALID_TOKEN = "error.2009";
	public static final String ERROR_INVALID_NONCE = "error.2010";
	public static final String ERROR_NOT_UNIQUE_REQUEST = "error.2011";
	public static final String ERROR_INVALID_SK = "error.2012";
	public static final String ERROR_SK_OUT_OF_TIME = "error.2013";
	public static final String ERROR_INVALID_APP_TOKEN = "error.2014";
	public static final String ERROR_INVALID_API_SIG = "error.2015";

	public static final String FORBIDDEN_URL = "forbidden.url";
	public static final String BIOMETRICS_URL = "biometrics.url";
	public static final String SEARCH_URL = "search.url";

	public static final String HEADER_ALLOW_ORIGIN = "header.allow.origin";
	public static final String HEADER_CREDENTIAL = "header.credential";
	public static final String HEADER_METHODS = "header.methods";
	public static final String HEADER_CONTENT_TYPE = "header.contentType";

}