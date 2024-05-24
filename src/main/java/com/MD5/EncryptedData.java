package com.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class EncryptedData 
{

	public String generateKey(String text) {
		 try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            byte[] md5Hash = md.digest(text.getBytes());

	            // Convert the byte array to a fixed-length 32-character hexadecimal string
	            StringBuilder hexString = new StringBuilder(32);
	            for (byte b : md5Hash) {
	                String hex = String.format("%02x", b);
	                hexString.append(hex);
	            }

	            return hexString.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            return "Error: " + e.getMessage();
	        }
	}
}
