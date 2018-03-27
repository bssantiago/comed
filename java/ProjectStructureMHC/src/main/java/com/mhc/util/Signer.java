package com.mhc.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Signs requests appropriately.
 * 
 */
public class Signer {
	
	private final String apiKey;
	private final String apiSecret;
	private String token = null;
	
	/**
	 * Create a signer which can create UNSIGNED or SIGNED requests.  The token needs to be
	 * set before creating AUTHENTICATED requests.
	 * 
	 * @param apiKey
	 * @param apiSecret
	 */
	public Signer(String apiKey, String apiSecret) {
		this(apiKey,apiSecret,null);
	}
	
	/**
	 * Create a signer which can create all request types.
	 * 
	 * @param apiKey
	 * @param apiSecret
	 * @param token
	 */
	public Signer(String apiKey, String apiSecret, String token) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.token = token;
	}
	
	/**
	 * Set the token used by this signer.
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * Get the current token.
	 * @return current token.
	 */
	public String getToken() {
		return token;
	}
	
	
	
	

	/**
	 * SDHA-1 hex encodes string.
	 * @param s
	 * @return SDHA-1 Hex encoding of the UTF-8 bytes of the string.
	 */
	public static String hexEncode(String s) {
		try {
			return DigestUtils.shaHex(s.getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException ex) {
			return DigestUtils.shaHex(s);
		}
	}
	
	public static String hexEncode256(String s) {
		try {
			return DigestUtils.sha256Hex(s.getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException ex) {
			return DigestUtils.sha256Hex(s);
		}
	}
	
	/**
	 * Construct a signature of a string
	 * 
	 * @param toSign String to sign.
	 * @return signature based on prepending the secret and hashing the result.
	 */
	public String signature(String toSign) {
		return hexEncode(apiSecret+toSign);
	}
}
