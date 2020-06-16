
package com.algorithms;

import javax.crypto.spec.SecretKeySpec;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

public class AES {

	private static SecretKeySpec secretKey;
    private static byte[] key;
    static String clearTextFile = "C:\\Users\\ehusanovic\\Desktop\\data.txt";
	static String cipherTextFile = "C:\\Users\\ehusanovic\\Desktop\\data2.txt";
	static String clearTextNewFile = "C:\\Users\\ehusanovic\\Desktop\\data3.txt";
 
	
	public static void main(String[] args) throws FileNotFoundException {
		final String secretKey = "ssshhhhhhhhhhh!!!!";
	     
//		String originalString = "howtodoinjava.com";
//		String encryptedString = AES.encrypt(originalString, secretKey);
//		String decryptedString = AES.decrypt(encryptedString, secretKey);
	//	AES.encrypt(new FileInputStream(clearTextFile), new FileOutputStream(cipherTextFile), secretKey);
		AES.decrypt(new FileInputStream(cipherTextFile), new FileOutputStream(clearTextNewFile), secretKey);
		System.out.println("DONE");
	     
	}
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static String encrypt(InputStream is,OutputStream os, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            os = new CipherOutputStream(os, cipher);
			writeData(is, os);
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(InputStream is,OutputStream os, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            os = new CipherOutputStream(os, cipher);
			writeData(is, os);
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    
    private static void writeData(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[1024];
		int numRead = 0;
		// read and write operation
		while ((numRead = is.read(buf)) >= 0) {
			os.write(buf, 0, numRead);
		}
		os.close();
		is.close();
	}
}
