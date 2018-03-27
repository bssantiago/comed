package com.mhc.services;

import com.mhc.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncryptedPropertyOverrideConfigurer extends
        PropertyOverrideConfigurer {
	
	private static final Logger LOGGER = Logger.getLogger(EncryptedPropertyOverrideConfigurer.class);
	
	public EncryptedPropertyOverrideConfigurer(String keyFile) throws IOException {
		String salt = new String(Files.readAllBytes(Paths.get(keyFile)));
	    Constants.setCORESERVICES_PROPERTY_SALT(salt);
	}
	
	protected String convertPropertyValue(String originalValue) {
		AESService aesService = new AESServiceImpl();
		String decrypted = ""; 
		try {
			decrypted = aesService.decrypt(Constants.getCORESERVICES_PROPERTY_SALT(), originalValue);
		} catch (Exception e) {
			LOGGER.error(null, e);
		}
		return decrypted;
	}
}
