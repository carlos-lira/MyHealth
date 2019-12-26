package services.i18n;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import utils.SessionUtils;

/**
 * Language bean. This bean is designed to show or change the actual language.
 * 
 * @author adlo
 */
@Named("language")
@SessionScoped
public class LanguageBean implements Serializable {
	private static final long serialVersionUID = 8157016164967209878L;

	/* Fields */
	private String localeCode;
	private Locale locale;
	private List<Locale> supportedLocales;

	@PostConstruct
	public void init() {
		this.locale = SessionUtils.getLocale();
		this.supportedLocales = new ArrayList<Locale>();
		this.supportedLocales.add(this.locale);
		for (Iterator<Locale> it = SessionUtils.getSupportedLocales(); it.hasNext();) {
			this.supportedLocales.add(it.next());
		}
	}

	/**
	 * @return a displayable language for a locale.
	 */
	public String getDisplayLanguage(Locale locale) {
		return locale.getDisplayLanguage();
	}

	/**
	 * @return the language of a locale.
	 */
	public String getLanguage(Locale locale) {
		return locale.getLanguage();
	}

	/**
	 * @return the country of a locale.
	 */
	public String getCountry(Locale locale) {
		return locale.getCountry();
	}

	/**
	 * @return the locale code of a locale.
	 */
	public String getLocaleCode(Locale locale) {
		return this.getLanguage(locale) + "_" + this.getCountry(locale);
	}

	/**
	 * Change the actual locale to a new one based on language and country.
	 * 
	 * @param e jsf change event.
	 */
	public void changeLanguage(ValueChangeEvent e) {
		String newLocaleValue = e.getNewValue().toString();
		String language = newLocaleValue.split("_")[0];
		String country = newLocaleValue.split("_")[1];
		Iterator<Locale> it = this.supportedLocales.iterator();
		while (it.hasNext()) {
			Locale l = it.next();
			if (l.getLanguage().equals(language) && l.getCountry().equals(country)) {
				Locale newLocale = new Locale(language, country);
				SessionUtils.getContext().getViewRoot().setLocale(newLocale);
				break;
			}
		}
	}

	// Getters & Setters
	/**
	 * @return the locale code.
	 */
	public String getLocaleCode() {
		return this.localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public List<Locale> getSupportedLocales() {
		return supportedLocales;
	}
}
