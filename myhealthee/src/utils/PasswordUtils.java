package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public final class PasswordUtils {

	/**
	 * Generate a hashed password.
	 * 
	 * @param password the raw password.
	 * @return a hashed password.
	 * @throws NoSuchAlgorithmException
	 */
	public static String createHashedPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO log
		}
		return password;
		
	}

	/**
	 * Verify if a password is correct.
	 * 
	 * @param rawPassword    the raw password to check.
	 * @param hashedPassword an already hashed password.
	 * @return true if is valid, false otherwise.
	 */
	public static boolean verifyPassword(final String rawPassword, final String hashedPassword) {
		if (rawPassword == null || hashedPassword == null) {
			return false;
		}
		return hashedPassword.equals(PasswordUtils.createHashedPassword(rawPassword));
	}
}
