package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.logging.impl.Log4jLogger;

/**
 * Security class. 
 * 
 * @author adlo
 */
public class Cypher {

	private static final Logger logger = Log4jLogger.getLogger(Cypher.class);
	
	/**
	 * Generate a hashed password.
	 * 
	 * @param password the raw password.
	 * @return a hashed password.
	 * @throws NoSuchAlgorithmException
	 */
	public static String createHashedPassword(final HashAlgorithm algorithm, final String password) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm.getAlgorithm());
			md.update(password.getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
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
	public static boolean verifyPassword(final HashAlgorithm algorithm, final String rawPassword, final String hashedPassword) {
		if (rawPassword == null || hashedPassword == null) {
			return false;
		}
		return hashedPassword.equals(createHashedPassword(algorithm, rawPassword));
	}
}
