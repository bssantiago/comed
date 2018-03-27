package com.mhc.services;

import com.mhc.exceptions.EncryptionException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;


public class AESServiceImpl implements AESService{
	
	private byte[] ivBytes = new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00 };
	
	public String encrypt(String key, String stringToEncrypt) throws EncryptionException {
		
	      try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

			  IvParameterSpec ivspec = new IvParameterSpec(ivBytes);

			  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			  cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);

			  byte[] encrypted = cipher.doFinal(stringToEncrypt.getBytes());

			  return Base64.encodeBase64String(encrypted);
		} catch (Exception e) {
			throw new EncryptionException(e);
		}  
	}
	
	public String decrypt(String key, String encryptedString) throws EncryptionException{
		
	      try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

			  IvParameterSpec ivspec = new IvParameterSpec(ivBytes);

			  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			  cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);
			  
			  byte[] encBytes = Base64.decodeBase64(encryptedString);
			  
			  byte[] original = cipher.doFinal(encBytes);

			  return new String(original);
		} catch (Exception e) {
			throw new EncryptionException(e);
		}
	}
	
	public byte[] encrypt(String key, byte[] dataToEncrypt) throws EncryptionException{
		
	      try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

			  IvParameterSpec ivspec = new IvParameterSpec(ivBytes);

			  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			  cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);

			  byte[] encrypted = cipher.doFinal(dataToEncrypt);

			  return Base64.encodeBase64(encrypted);
		} catch (Exception e) {
			throw new EncryptionException(e);
		}  
	}
	
	public byte[] decrypt(String key, byte[] encryptedData) throws EncryptionException{
		
	      try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

			  IvParameterSpec ivspec = new IvParameterSpec(ivBytes);

			  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			  cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);
			  
			  byte[] encBytes = Base64.decodeBase64(encryptedData);
			  
			  byte[] original = cipher.doFinal(encBytes);

			  return original;
		} catch (Exception e) {
			throw new EncryptionException(e);
		}
	}



	@Override
	public void encryptStream(String key, InputStream inputStream,
			OutputStream outputStream) {
		  try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

			  IvParameterSpec ivspec = new IvParameterSpec(ivBytes);

			  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			  cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);
			  
			  CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
			  
			  IOUtils.copy(inputStream, cipherOutputStream);
			
			  cipherOutputStream.close();
			  inputStream.close();
			  outputStream.close();
		} catch (Exception e) {
			throw new EncryptionException(e);
		}  
	}

	@Override
	public void decryptStream(String key, InputStream inputStream,
			OutputStream outputStream) {
		  try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

			  IvParameterSpec ivspec = new IvParameterSpec(ivBytes);

			  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			  cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);
			  
			  CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
			  IOUtils.copy(cipherInputStream, outputStream);
			
			  outputStream.close();
			  cipherInputStream.close();
			  inputStream.close();
		} catch (Exception e) {
			throw new EncryptionException(e);
		}		
		
	}


}
