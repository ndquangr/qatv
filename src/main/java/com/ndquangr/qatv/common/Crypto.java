package com.ndquangr.qatv.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * This class for generate, encrypt, decrypt license key
 *
 */
@SuppressWarnings("restriction")
public class Crypto {
	private static SecretKey key;
	private final static String password = "945fc9611f55fd0e183fb8b044f1";
	private final static String salt = "6uRYDPpbLU7dnVCKCceyjw";

	/**
	 * Generates the encryption key. using "des" algorithm
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeySpecException
	 */
	protected static void generateKey() throws NoSuchAlgorithmException,
			UnsupportedEncodingException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(),
				65536, 128);
		SecretKey tmp = factory.generateSecret(spec);

		key = new SecretKeySpec(tmp.getEncoded(), "AES");
	}

	/**
	 * Encrypt raw license key
	 * 
	 * @param message
	 * @return encrypted license key
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidAlgorithmParameterException
	 */
	protected static String encrypt(String message)
			throws IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException {
		byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		// Get a cipher object.
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);

		// Gets the raw bytes to encrypt, UTF8 is needed for
		// having a standard character set
		byte[] stringBytes = message.getBytes("UTF8");

		// encrypt using the cypher
		byte[] raw = cipher.doFinal(stringBytes);

		// converts to base64 for easier display.
		// Encoder encoder = Base64.getEncoder();
		// String base64 = encoder.encodeToString(raw);
		BASE64Encoder encoder = new BASE64Encoder();
		String base64 = encoder.encode(raw);

		return base64;
	}

	/**
	 * decrypt license key
	 * 
	 * @param encrypted
	 * @return clear license key
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 * @throws InvalidAlgorithmParameterException
	 */
	protected static String decrypt(String encrypted)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException,
			InvalidAlgorithmParameterException {
		byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		// Get a cipher object.
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, ivspec);

		// decode the BASE64 coded message
		// Decoder decoder = Base64.getDecoder();
		// byte[] raw = decoder.decode(encrypted);
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] raw = decoder.decodeBuffer(encrypted);

		// decode the message
		byte[] stringBytes = cipher.doFinal(raw);

		// converts the decoded message to a String
		String clear = new String(stringBytes, "UTF8");
		return clear;
	}

}
