package com.mhc.services;

import java.io.InputStream;
import java.io.OutputStream;

public interface AESService {

	public String encrypt(String key, String stringToEncrypt);
	public String decrypt(String key, String encryptedString);
	
	public byte[] encrypt(String key, byte[] dataToEncrypt);
	public byte[] decrypt(String key, byte[] dataToDecrypt);
	
	public void encryptStream(String key, InputStream inputStream, OutputStream outputStream);
	public void decryptStream(String key, InputStream inputStream, OutputStream outputStream);
}
