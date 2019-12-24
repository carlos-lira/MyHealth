package services.i18n;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
	private Locale locale;
	private List<Locale> supportedLocales;

	@PostConstruct
	public void init() {
		this.locale = SessionUtils.getLocale();
		this.supportedLocales = new ArrayList<Locale>();
		for (Iterator<Locale> it = SessionUtils.getSupportedLocales(); it.hasNext();) {
			this.supportedLocales.add(it.next());
		}
	}

	/**
	 * @return the actual locale.
	 */
	public Locale getLocale() {
		return this.locale;
	}

	/**
	 * @return the actual language
	 */
	public String getLanguage() {
		return this.locale.getLanguage();
	}

	/**
	 * @return the actual country,
	 */
	public String getCountry() {
		return this.locale.getCountry();
	}

	/**
	 * Change the actual locale to a new one based on language and country.
	 * 
	 * @param language
	 * @param country
	 */
	public void changeLanguage(String language, String country) {
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
}
