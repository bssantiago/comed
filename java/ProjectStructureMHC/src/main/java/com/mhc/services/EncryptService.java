package com.mhc.services;

import org.apache.commons.lang3.StringUtils;

import com.mhc.util.InitUtil;

public class EncryptService {

	private static final AESService aesService = new AESServiceImpl();

	public static String encryptStringDB(String string) {
		return encryptString(string, InitUtil.getDocKey());
	}

	public static String decryptStringDB(String string) {
		if (!StringUtils.isNotEmpty(string)) {
			return "";
		}
		return decryptString(string, InitUtil.getDocKey());
	}
	
	
	public static String encryptStringSaltSharedKey(String string) {
		return encryptString(string, InitUtil.getSalt());
	}

	public static String decryptStringSaltSharedKey(String string) {
		return decryptString(string, InitUtil.getSalt());
	}
	
	public static String encryptStringLog(String string) {
		return encryptString(string, InitUtil.getLogKey());
	}

	public static String decryptStringLog(String string) {
		return decryptString(string, InitUtil.getLogKey());
	}
	
	private static String encryptString(String string, String salt) {
		String result = aesService.encrypt(salt, string);
		return result;
	}

	private static String decryptString(String string, String salt) {
		String result = aesService.decrypt(salt, string);
		return result;
	}

}
