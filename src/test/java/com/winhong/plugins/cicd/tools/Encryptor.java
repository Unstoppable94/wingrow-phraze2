package com.winhong.plugins.cicd.tools;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import java.security.NoSuchAlgorithmException;

 

public class Encryptor {
	
						  // xpor
	private static final String key = "libbsd0 libdns-e"; // 128 bit key
		// XVFcEFYu4dm62P7+wFOChA==
	private	static final String initVector = "RandomInitVector"; // 16 bytes IV
		
	public static String encrypt( String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			System.out.println("encrypted string: " + Base64.encodeBase64String(encrypted));

			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {

		KeyGenerator keygenerator = null;
		try {
			keygenerator = KeyGenerator.getInstance("AES");
			keygenerator.init(128);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//SecretKey myDesKey = keygenerator.generateKey();

		//String key = new String(myDesKey.getEncoded());
		//System.out.println("key : " + key.length());
	//	key="Bar1wwwwBar12345";

		//System.out.println("key : " + Base64.encodeBase64String(myDesKey.getEncoded()));
 		

		System.out.println(decrypt(encrypt("Hello 中国")));
	}
}