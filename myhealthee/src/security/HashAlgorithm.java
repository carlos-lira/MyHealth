package security;

/**
 * Enums of algorithms types.
 * 
 * @author adlo
 */
public enum HashAlgorithm {
	MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256"), SHA512("SHA-512");

	private String algorithm;

	private HashAlgorithm(String s) {
		this.algorithm = s;
	}

	/**
	 * Getter to get the string value of enum constant
	 * 
	 * @return String value of hash algorithm
	 */
	public String getAlgorithm() {
		return this.algorithm;
	}
}
