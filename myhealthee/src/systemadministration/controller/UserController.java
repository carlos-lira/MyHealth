package systemadministration.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import entity.User;
import systemadministration.dao.SystemAdministrationFacadeRemote;
import utils.SessionUtils;

@Named("user")
@SessionScoped
public class UserController implements Serializable {
	private static final long serialVersionUID = -9127958441792875264L;

	@EJB
	private SystemAdministrationFacadeRemote ejb;

	/* Fields */
	private String id;
	private String password;

	/**
	 * Login Action.
	 * 
	 * @throws IOException
	 */
	public String login() {
		User u = ejb.login(id, password);
		if (u == null) {
			SessionUtils.getContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong id or password", ""));
			return null;
		}
		SessionUtils.getSession().setAttribute("user", u);
		return "homeView";
	}

	/**
	 * Logout Action.
	 */
	public String logout() {
		SessionUtils.getSession().invalidate();
		SessionUtils.getContext().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout successful", ""));
		return "loginView";
	}

	/**
	 * RegisterAdministrator Action.
	 */
	public void registerAdministrator() {
		// TODO
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
}
