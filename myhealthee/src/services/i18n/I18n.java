package services.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import utils.SessionUtils;

/**
 * Internationalization class to get the strings from the resources bundle.
 * 
 * @author adlo
 */
public final class I18n {

	private static final String BUNDLE_NAME = "/META-INF/resources/myhealth";
	
	/**
	 * Hide constructor
	 */
	private I18n() {
		// nop
	}

	/**
	 * Get message from the resources bundle.
	 * 
	 * @param key the key to get the message string.
	 * @return the message string, the key itself if it didn't find.
	 */
	public static String translate(String key, String... parameters) {
		try {
			String bundleName = SessionUtils.getMessageBundle();
			if (bundleName == null) {
				bundleName = BUNDLE_NAME;
			}
			Locale correntLocale = SessionUtils.getLocale();
			ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, correntLocale);
			String message = resourceBundle.getString(key);
			return replaceParameters(message, parameters);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	// PRIVATE METHODS
	private static String replaceParameters(String message, String... parameters) {
		if (parameters.length > 0) {
			String tmp = message.replace("#", "%s");
			return String.format(tmp, parameters);
		}
		return message;
	}
}
