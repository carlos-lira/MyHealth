package components.systemadministration.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import configuration.ApplicationInitializer;
import entity.User;
import services.i18n.I18n;
import utils.Messages;
import utils.SessionUtils;

/**
 * Login managed bean.
 * 
 * @author adlo
 */
@Named("login")
@RequestScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1405001430585847646L;

	@EJB
	private SystemAdministrationFacadeRemote ejb;

	/* Fields */
	private String id;
	private String password;
	private boolean rememberMe;
	private String resetEmail;

	// ACTIONS
	/**
	 * Login Action.
	 */
	public String login() {
		User u = ejb.login(id, password);
		if (u != null) {
			SessionUtils.addUser(u);
			if (rememberMe) {
				// TODO rememberMe cookie
			}
			return "homeView";
		}
		Messages.addErrorGlobalMessage(I18n.translate("systemAdministration.error.000001"));
		return null;
	}

	/**
	 * Logout Action
	 */
	public String logout() {
		SessionUtils.getSession().invalidate();
		Messages.addInfoGlobalMessage(I18n.translate("systemAdministration.info.000001"));
		return "loginView";
	}

	/**
	 * Reset Password Action.
	 */
	public String resetPassword() {
		if (ApplicationInitializer.SMTP_HOST == null || ApplicationInitializer.SMTP_HOST.equals("")) {
			Messages.addWarnGlobalMessage(I18n.translate("systemAdministration.warn.000001"));
			return null;
		}
		// Try to send the email
		User u = ejb.getUser(this.resetEmail);
		if (u != null) {
//			MailData data = new MailData("Recover passoword", "...", u.getEmail());
//			SmtpService mailService = new SmtpService();
//			mailService.sendEmail(data);
		}
		return null;
	}

	// Getters & Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getResetEmail() {
		return resetEmail;
	}

	public void setResetEmail(String resetEmail) {
		this.resetEmail = resetEmail;
	}
}
