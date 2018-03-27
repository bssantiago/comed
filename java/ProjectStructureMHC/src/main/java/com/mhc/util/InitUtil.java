package com.mhc.util;

import lombok.Data;

public final @Data class InitUtil {
	
	private InitUtil(){
		
	}
	
	private static String salt;
    private static String docKey;
    private static String logKey;

	public static String getSalt() {
		return salt;
	}
	
	public static void setSalt(String saltVal) {
		salt = saltVal;
	}
	
	public static String getDocKey() {
		return docKey;
	}
	
	public static void setDocKey(String docKeyVal) {
		docKey = docKeyVal;
	}
	
	public static String getLogKey() {
		return logKey;
	}
	
	public static void setLogKey(String logKeyVal) {
		logKey = logKeyVal;
	}
    
    
    
}