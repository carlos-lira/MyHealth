package components.systemadministration.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.User;
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
		Messages.addErrorGlobalMessage("The username, email or password are not corrects");
		return null;
	}

	/**
	 * Logout Action
	 */
	public String logout() {
		SessionUtils.getSession().invalidate();
		Messages.addInfoGlobalMessage("You are now logged out.");
		return "loginView";
	}

	/**
	 * Reset Password Action.
	 */
	public String resetPassword() {
		Messages.addWarnGlobalMessage("Unable to send emails, smtp service not configured.");
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
