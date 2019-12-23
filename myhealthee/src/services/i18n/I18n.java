package services.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import configuration.ApplicationInitializer;
import utils.SessionKeys;
import utils.SessionUtils;

/**
 * Internationalization class to get the strings from the resources bundle.
 * 
 * @author adlo
 */
public abstract class I18n {

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
	public static String translate(String key, String...parameters) {
		// Try to get the language from session.
		String language = String.valueOf(SessionUtils.getValue(SessionKeys.LANGUAGE));
		String country = String.valueOf(SessionUtils.getValue(SessionKeys.COUNTRY));
		// If the language or the contry are not in session get the default one.
		if (language == null || country == null) {
			language = ApplicationInitializer.LANGUAGE;
			country = ApplicationInitializer.COUNTRY;
		}
		Locale correntLocale = new Locale(language, country);
		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(ApplicationInitializer.RESOURCE_BUNDLE, correntLocale);
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
