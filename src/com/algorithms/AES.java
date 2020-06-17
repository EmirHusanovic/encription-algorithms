
package com.algorithms;

import javax.crypto.spec.SecretKeySpec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class AES {

	private static SecretKeySpec secretKey;
	private static byte[] key;
	static String filePath = "";
	static String secret = "ssshhhhhhhhhhh!!!!";

	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String encrypt() {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			FileInputStream is = new FileInputStream(filePath);
			FileOutputStream o = new FileOutputStream(createFile("encr"));
			OutputStream os = new CipherOutputStream(o, cipher);
			writeData(is, os);
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static void decrypt() {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			FileInputStream ii = new FileInputStream(filePath);
			FileOutputStream os = new FileOutputStream(createFile("decr"));
			InputStream is = new CipherInputStream(ii, cipher);
			writeData(is, os);
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}

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

	private static String createFile(String type) {
		String pp = getFullPath(filePath) + type + "-" + getFileName(filePath);
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

	private void deleteFile() {
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
		String operSys = System.getProperty("os.name").toLowerCase();
		String filePath = "";
		if (operSys.contains("win")) {
			filePath = path.substring(path.lastIndexOf("\\") + 1);
		} else if (operSys.contains("mac")) {
			filePath = path.substring(path.lastIndexOf("/"));
		}
		;
		return filePath;
	}

	private static String getFullPath(String path) {
		return path.substring(0, path.lastIndexOf("\\") + 1);
	}
}
