package services.mail;

/**
 * SMTP Hosts enumeration.
 *
 * @author adlo
 */
enum SmtpHosts {
	O365("smtp.office365.com", true), GMAIL("smtp.gmail.com", true), CUSTOM("", false);

	/* Fields */
	private String host;
	private boolean useStmpIdentifierAsFromEmail;

	/**
	 * Constructor.
	 * 
	 * @param host
	 */
	private SmtpHosts(String host, boolean useStmpIdentifierAsFromEmail) {
		this.host = host;
		this.useStmpIdentifierAsFromEmail = useStmpIdentifierAsFromEmail;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	public boolean useStmpIdentifierAsFromEmail() {
		return useStmpIdentifierAsFromEmail;
	}

	/**
	 * Get SmtpHosts.
	 * 
	 * @param host the host identifier
	 * @return the SmtpHosts.
	 */
	public static SmtpHosts getFor(String host) {
		for (SmtpHosts smtpHost : values()) {
			if (smtpHost.getHost().equals(host)) {
				return smtpHost;
			}
		}
		return CUSTOM;
	}

	/**
	 * Get host from a String representation.
	 * 
	 * @param host the host
	 * @return a registered SmtpHosts from a string representation.
	 */
	public static String getHostFrom(String host) {
		for (SmtpHosts smtpHost : values()) {
			if (smtpHost.getHost().equals(host)) {
				return smtpHost.getHost();
			}
		}
		return host;
	}
}
