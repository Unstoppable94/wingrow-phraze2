package com.winhong.plugins.cicd.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.rometools.rome.io.impl.Base64;

public class CipherByteArrayExampleTest
{
	public static void main(String[] argv) throws InvalidAlgorithmParameterException {

		try{
			 testEncrypt();
	        String initVector = "RandomInitVector"; // 16 bytes IV


		    KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
		    SecretKey myDesKey = keygenerator.generateKey();
		    String key=new String(myDesKey.getEncoded());
		    
		    System.out.println("key : " + Base64.encode(key));

		    //SecretKey originalKey = SecretKeySpec(encodedKey, 0, encodedKey.length, "AES")
		    SecretKey originalKey = new SecretKeySpec(myDesKey.getEncoded(), 0, myDesKey.getEncoded().length, "AES");
		    
		    
		    Cipher desCipher;
		    byte[] raw = myDesKey.getEncoded();
		    //key.getBytes(Charset.forName("UTF-8"));

		    // Create the cipher
		    IvParameterSpec ivParameterSpec = new IvParameterSpec(raw);
		    
		    desCipher =Cipher.getInstance("AES/CBC/PKCS5Padding");
		    //Cipher.getInstance("DES/ECB/PKCS5Padding");
		    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		    desCipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		    // Initialize the cipher for encryption
		    //desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

		    //sensitive information
		    byte[] text = "No body can see me".getBytes();

		    System.out.println("Text [Byte Format] : " + text);
		    System.out.println("Text : " + new String(text));

		    // Encrypt the text
		    byte[] textEncrypted = desCipher.doFinal(text);

		    System.out.println("Text Encryted : " + textEncrypted);

		    
		    Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		   
		    decryptCipher.init(Cipher.DECRYPT_MODE, skeySpec);
		    
		    // Initialize the same cipher for decryption
		    //desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
		   // IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
		   // decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
		    // Decrypt the text
		    byte[] textDecrypted = decryptCipher.doFinal(textEncrypted);

		    System.out.println("Text Decryted : " + new String(textDecrypted));
		   
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch(NoSuchPaddingException e){
			e.printStackTrace();
		}catch(InvalidKeyException e){
			e.printStackTrace();
		}catch(IllegalBlockSizeException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		}

	}
	
	
	public static void testEncrypt() {
		  try {
		    String s = "wwwww====Have a nice day.";

		    // Generate key
		    KeyGenerator kgen = KeyGenerator.getInstance("AES");
		    kgen.init(128);
		    SecretKey aesKey = kgen.generateKey();

		    // Encrypt cipher
		    Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey);

		    // Encrypt
		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);
		    cipherOutputStream.write(s.getBytes());
		    cipherOutputStream.flush();
		    cipherOutputStream.close();
		    byte[] encryptedBytes = outputStream.toByteArray();

		    // Decrypt cipher
		    Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
		    decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);

		    // Decrypt
		    outputStream = new ByteArrayOutputStream();
		    ByteArrayInputStream inStream = new ByteArrayInputStream(encryptedBytes);
		    CipherInputStream cipherInputStream = new CipherInputStream(inStream, decryptCipher);
		    byte[] buf = new byte[1024];
		    int bytesRead;
		    while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
		        outputStream.write(buf, 0, bytesRead);
		    }

		    System.out.println( new String(outputStream.toByteArray()));

		  } 
		  catch (Exception ex) {
		    ex.printStackTrace();
		  }
		}
}
