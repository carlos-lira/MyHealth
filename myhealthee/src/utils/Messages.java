package utils;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

public abstract class Messages {

	/**
	 * Add a global message.
	 * 
	 * @param severity facess message severity.
	 * @param message  the message
	 */
	public static void addGlobalMessage(Severity severity, String message) {
		SessionUtils.getExternalContext().getFlash().setKeepMessages(true);
		SessionUtils.getContext().addMessage(null, new FacesMessage(severity, message, null));
	}
	
	/**
	 * Add a message.
	 * 
	 * @param severity facess message severity.
	 * @param message  the message
	 */
	public static void addMessage(Severity severity, String message) {
		SessionUtils.getContext().addMessage(null, new FacesMessage(severity, message, null));
	}
	
	/**
	 * Add info global message
	 * 
	 * @param message the message
	 */
	public static void addInfoGlobalMessage(String message) {
		addGlobalMessage(FacesMessage.SEVERITY_INFO, message);
	}
	
	/**
	 * Add warn global message
	 * 
	 * @param message the message
	 */
	public static void addWarnGlobalMessage(String message) {
		addGlobalMessage(FacesMessage.SEVERITY_WARN, message);
	}
	
	/**
	 * Add error global message
	 * 
	 * @param message the message
	 */
	public static void addErrorGlobalMessage(String message) {
		addGlobalMessage(FacesMessage.SEVERITY_ERROR, message);
	}
	
	/**
	 * Add info message
	 * 
	 * @param message the message
	 */
	public static void addInfoMessage(String message) {
		addMessage(FacesMessage.SEVERITY_INFO, message);
	}
	
	/**
	 * Add info message
	 * 
	 * @param message the message
	 */
	public static void addWarnMessage(String message) {
		addMessage(FacesMessage.SEVERITY_WARN, message);
	}
	
	/**
	 * Add info message
	 * 
	 * @param message the message
	 */
	public static void addErrorMessage(String message) {
		addMessage(FacesMessage.SEVERITY_ERROR, message);
	}
}
