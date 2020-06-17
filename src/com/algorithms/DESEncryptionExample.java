package com.algorithms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESEncryptionExample {

//	    private static byte[] key;
//	    private static SecretKeySpec secretKey;
	private static final byte[] iv = { 11, 22, 33, 44, 99, 88, 77, 66 };
	static String path = "/Users/emir/Desktop/";
static	String filePath = "/Users/emir/Desktop/encrypted-emir.txt";

	public static void main(String[] args) throws InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, FileNotFoundException, IOException {
		final String secretKey = "ssshhhhhhhhhhh!!!!";
		
		try {
			// create SecretKey using KeyGenerator
			SecretKey key = KeyGenerator.getInstance("DES").generateKey();
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			// get Cipher instance and initiate in encrypt mode

			// get Cipher instance and initiate in decrypt mode

			// method to encrypt clear text file to encrypted file
			//encrypt(new FileInputStream(filePath), new IvParameterSpec(iv));

			// method to decrypt encrypted file to clear text file
			
			
			decrypt(filePath, new IvParameterSpec(iv));
			System.out.println("DONE");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}
	
	private static SecretKey setKey() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		String password = "abcd1234";
		byte[] bytes = password.getBytes();
		DESKeySpec keySpec = new DESKeySpec(bytes);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(keySpec);
		return key;
	}

	private static void encrypt(InputStream is, AlgorithmParameterSpec paramSpec)
			throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidKeySpecException, InvalidAlgorithmParameterException {
		
		Cipher encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		encryptCipher.init(Cipher.ENCRYPT_MODE, setKey(), paramSpec);
		// create CipherOutputStream to encrypt the data using encryptCipher
		FileOutputStream o = new FileOutputStream(createFile("encrypted"));
		OutputStream os = new CipherOutputStream(o, encryptCipher);
		writeData(is, os);
	}

	private static void decrypt(String path, AlgorithmParameterSpec paramSpec)
			throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidKeySpecException, InvalidAlgorithmParameterException {
		Cipher decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		decryptCipher.init(Cipher.DECRYPT_MODE, setKey(), paramSpec);
		// create CipherOutputStream to decrypt the data using decryptCipher
		FileInputStream ii = new FileInputStream(path);
		FileOutputStream os = new FileOutputStream(createFile("decrypted"));
		InputStream is = new CipherInputStream(ii, decryptCipher);
		writeData(is, os);
	}

	// utility method to read data from input stream and write to output stream
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

	private static String createFile(String type) {

	String pp  =  path + type + "-" +getFileName(filePath);
	
		try {
			File myObj = new File(pp);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getAbsolutePath());
				filePath = myObj.getAbsolutePath();
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return filePath;
		
	}

	private static void deleteFile() {
		try {
			File file = new File(filePath);
			if (file.delete()) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("Failed to delete the file");
			}
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	private static String getFileName(String path) {
		return path.substring(path.lastIndexOf("/") + 1);
		
	}

}
