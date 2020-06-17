package com.algorithms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class TripleDES {
	private static final byte[] iv = { 11, 22, 33, 44, 99, 88, 77, 66 };
	public String filePath = "";

	private static SecretKey setKey() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		String password = "abcd1234";
		byte[] bytes = password.getBytes();
		DESedeKeySpec  keySpec = new DESedeKeySpec(new byte[24]);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey key = keyFactory.generateSecret(keySpec);
		return key;
	}

	public void encrypt() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidKeySpecException, InvalidAlgorithmParameterException {

		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		Cipher encryptCipher = Cipher.getInstance("DESede/CBC/NoPadding");
		encryptCipher.init(Cipher.ENCRYPT_MODE, setKey(), paramSpec);
		FileInputStream is = new FileInputStream(filePath);
		FileOutputStream o = new FileOutputStream(createFile("encr"));
		OutputStream os = new CipherOutputStream(o, encryptCipher);
		writeData(is, os);
	}

	public void decrypt() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidKeySpecException, InvalidAlgorithmParameterException {

		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		Cipher decryptCipher = Cipher.getInstance("DESede/CBC/NoPadding");
		decryptCipher.init(Cipher.DECRYPT_MODE, setKey(), paramSpec);
		FileInputStream ii = new FileInputStream(filePath);
		FileOutputStream os = new FileOutputStream(createFile("decr"));
		InputStream is = new CipherInputStream(ii, decryptCipher);
		writeData(is, os);
	}

	// utility method to read data from input stream and write to output stream
	private void writeData(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[1024];
		int numRead = 0;
		// read and write operation
		while ((numRead = is.read(buf)) >= 0) {
			os.write(buf, 0, numRead);
		}
		os.close();
		is.close();
	}

	private String createFile(String type) {
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

	private String getFileName(String path) {
		String operSys = System.getProperty("os.name").toLowerCase();
		String filePath = "";
		if (operSys.contains("win")) {
			filePath = path.substring(path.lastIndexOf("\\") + 1);
		} else if (operSys.contains("mac")) {
			filePath = path.substring(path.lastIndexOf("/"));
		};
		return filePath;
	}

	private String getFullPath(String path) {
		return path.substring(0, path.lastIndexOf("\\") + 1);
	}

}
