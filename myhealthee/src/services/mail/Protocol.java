package services.mail;

/**
 * Enum to add the different smtp euthentication methods
 *
 * @author adlo
 */
public enum Protocol {
	SSL("ssl", "465"), TLS("tls", "587"), NO_AUTHENTICATION("", "");

	/* Fields */
	private final String protocol;
	private final String port;

	/**
	 * Constructor
	 *
	 * @param protocol the protocol
	 */
	private Protocol(String protocol, String port) {
		this.protocol = protocol;
		this.port = port;
	}

	/**
	 * Get Smtp authentication.
	 *
	 * @param protocol the authentication method identifier.
	 * @return the SmtpAuthentication
	 */
	public static Protocol getFor(String protocol) {
		for (Protocol auth : values()) {
			if (auth.getProtocol().equalsIgnoreCase(protocol)) {
				return auth;
			}
		}
		return NO_AUTHENTICATION;
	}

	/**
	 * @return smtp protocol.
	 */
	public String getProtocol() {
		return this.protocol;
	}

	/**
	 * @return the default port.
	 */
	public String getDefaultPort() {
		return this.port;
	}

	/**
	 * Check if port is a valid port and return. If the port is not a valid port
	 * return the default port.
	 *
	 * @param port the port to check
	 * @return the port or default port if port is null.
	 */
	public String getDefaultOnNull(String port) {
		if (port == null) {
			return this.port;
		}
		return port;
	}
}
