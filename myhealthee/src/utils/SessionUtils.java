package utils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	 * @return the external context.
	 */
	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
}
