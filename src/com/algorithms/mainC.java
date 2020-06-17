package com.algorithms;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

public class mainC {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException {
//		DESEncryptionExample des = new DESEncryptionExample();
//		des.filePath = "C:\\Users\\ehusanovic\\Desktop\\emir.txt";
//		des.encrypt();
		TripleDES aes = new TripleDES();
		aes.filePath = "C:\\Users\\ehusanovic\\Desktop\\encr-emir.txt";
		aes.decrypt();
	}

}
