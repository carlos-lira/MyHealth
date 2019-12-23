package configuration;

/**
 * This class will load the configuration of the application.
 * 
 * @author adlo
 */
public final class ApplicationInitializer {
	
	/* Constants */
	public static final Boolean DEBUG_MODE;
	public static final String RESOURCE_BUNDLE;
	public static final String LANGUAGE;
	public static final String COUNTRY;
	// SMTP configuration
	public static final String SMTP_HOST;
	public static final String SMTP_PORT;
	public static final String SMTP_PROTOCOL;
	public static final String SMTP_ID;
	public static final String SMTP_PASSWORD;
	public static final String SMTP_FROM_EMAIL;
	
	/**
	 * Configure variables
	 */
	static {
		DEBUG_MODE = Boolean.valueOf(System.getProperty("debug", "true"));
		RESOURCE_BUNDLE = System.getProperty("resource", "myhealth");
		LANGUAGE = System.getProperty("language", "en");
		COUNTRY = System.getProperty("country", "US");

		SMTP_HOST = System.getProperty("smtp_host", "");
		SMTP_PORT = System.getProperty("smtp_port", "");
		SMTP_PROTOCOL = System.getProperty("smtp_protocol", "");
		SMTP_ID = System.getProperty("smtp_id", "");
		SMTP_PASSWORD = System.getProperty("smtp_password", "");
		SMTP_FROM_EMAIL = System.getProperty("smtp_from_email", "");
	}
}
