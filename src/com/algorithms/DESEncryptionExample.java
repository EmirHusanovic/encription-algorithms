package com.algorithms;



	import java.io.File;
import java.io.FileInputStream;
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

		public static void main(String[] args) throws InvalidKeySpecException, InvalidAlgorithmParameterException {
			final String secretKey = "ssshhhhhhhhhhh!!!!";
			String clearTextFile = "C:\\Users\\ehusanovic\\Desktop\\data.txt";
			String cipherTextFile = "C:\\Users\\ehusanovic\\Desktop\\data2.txt";
			String clearTextNewFile = "C:\\Users\\ehusanovic\\Desktop\\data3.txt";
			
//			 try {
//			      File myObj = new File("C:\\Users\\ehusanovic\\Desktop\\eee.txt");
//			      if (myObj.createNewFile()) {
//			        System.out.println("File created: " + myObj.getAbsolutePath());
//			      } else {
//			        System.out.println("File already exists.");
//			      }
//			    } catch (IOException e) {
//			      System.out.println("An error occurred.");
//			      e.printStackTrace();
//			    }

			try {
				// create SecretKey using KeyGenerator
				SecretKey key = KeyGenerator.getInstance("DES").generateKey();
				AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
				System.out.println("Params" + paramSpec.hashCode());

				// get Cipher instance and initiate in encrypt mode
				

				// get Cipher instance and initiate in decrypt mode
				

				// method to encrypt clear text file to encrypted file
				//encrypt(new FileInputStream(clearTextFile), new FileOutputStream(cipherTextFile),new IvParameterSpec(iv));

				// method to decrypt encrypted file to clear text file
			decrypt(new FileInputStream(cipherTextFile), new FileOutputStream(clearTextNewFile),new IvParameterSpec(iv) );
				System.out.println("DONE");
			} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
					| IOException e) {
				e.printStackTrace();
			}

		}
		

		private static void encrypt(InputStream is, OutputStream os, AlgorithmParameterSpec paramSpec) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException {
			//setKey(secret);
			String password = "abcd1234";
			byte[] bytes = password.getBytes();
			DESKeySpec keySpec = new DESKeySpec(bytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);
			System.out.println(new String(key.getEncoded()));
			Cipher encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			// create CipherOutputStream to encrypt the data using encryptCipher
			os = new CipherOutputStream(os, encryptCipher);
			writeData(is, os);
		}

		private static void decrypt(InputStream is, OutputStream os, AlgorithmParameterSpec paramSpec) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException {
			String password = "abcd1234";
			byte[] bytes = password.getBytes();
			DESKeySpec keySpec = new DESKeySpec(bytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);
			Cipher decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			decryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			// create CipherOutputStream to decrypt the data using decryptCipher
			is = new CipherInputStream(is, decryptCipher);
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

	}


