package com.mhc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.mhc.services.AESService;
import com.mhc.services.AESServiceImpl;

public class EncryptTool {

	private static final String keyfile = "c:/key.txt";
	private static final String YES = "yes";
	private static final String NO = "no";
	private static final Logger LOG = Logger.getLogger(EncryptTool.class);
	
	public static void main(String[] args) {
		try {
			String salt = new String(Files.readAllBytes(Paths.get(keyfile)));
			System.out.println("Salt: " + salt);
			AESService encryptor = new AESServiceImpl();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			boolean end = false;
			while (!end) {
				System.out.println("Enter key for encode:");
				String input = br.readLine();
				String output = encryptor.encrypt(salt, input);
				System.out.println("THe encrypted result id: " + output);
				while (!(input.equalsIgnoreCase(NO) || input.equalsIgnoreCase(YES))) {
					System.out.println("Do you want to continue? (yes/no)");
					input = br.readLine();
				}
				end = input.equalsIgnoreCase(NO);
			}

		} catch (IOException e) {
			LOG.error(e);
		}

	}

}
