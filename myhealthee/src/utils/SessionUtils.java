package utils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.User;

/**
 * JSF Session utilities.
 * 
 * @author adlo
 */
public abstract class SessionUtils {

	/**
	 * @return the current session.
	 */
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	/**
	 * @return the current request.
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * @return the context.
	 */
	public static FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Set a value to the session.
	 * 
	 * @param key a key
	 * @param o   the serializable object to store.
	 */
	public static void setValue(String key, Serializable o) {
		getSession().setAttribute(key, o);
	}

	/**
	 * Get value from session.
	 * 
	 * @param key the key to get the value from session.
	 * @return the object stored in session.
	 */
	public static Object getValue(String key) {
		return getSession().getAttribute(key);
	}

	/**
	 * @return the external context.
	 */
	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * Add user to the session.
	 * 
	 * @param user the user.
	 */
	public static void addUser(User user) {
		setValue(SessionKeys.USER, user);
	}

	/**
	 * @return the user stored in session.
	 */
	public static User getUser() {
		return (User) getValue(SessionKeys.USER);
	}
	
	/**
	 * @return the message bundle name.
	 */
	public static String getMessageBundle() {
		return getContext().getApplication().getMessageBundle();
	}

	/**
	 * @return the actual locale.
	 */
	public static Locale getLocale() {
		return getContext().getViewRoot().getLocale();
	}
	
	/**
	 * @return an iterator with the supported locales.
	 */
	public static Iterator<Locale> getSuportedLocales() {
		return getContext().getApplication().getSupportedLocales();
	}
}
