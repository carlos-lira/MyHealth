package systemadministration.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import entity.Doctor;
import entity.User;
import entity.imp.Administrator;
import entity.imp.Patient;
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
	private boolean rememberMe;
	private String resetEmail;
	
	// New user
	private Patient newPatient;
	private Doctor newDoctor;
	private Administrator newAdministrator;

	/**
	 * Login Action.
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
	 * Reset Password Action.
	 */
	public String resetPassword() {
		// TODO
		return null;
	}
	
	/**
	 * Register patient Action.
	 */
	public String registerPatient() {
		// TODO
		return null;
	}
	
	/**
	 * Register Doctor Action.
	 */
	public String registerDoctor() {
		// TODO
		return null;
	}
	
	/**
	 * Register Administrator Action.
	 */
	public String registerAdministrator() {
		// TODO
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

	public Patient getNewPatient() {
		return newPatient;
	}

	public void setNewPatient(Patient newPatient) {
		this.newPatient = newPatient;
	}

	public Doctor getNewDoctor() {
		return newDoctor;
	}

	public void setNewDoctor(Doctor newDoctor) {
		this.newDoctor = newDoctor;
	}

	public Administrator getNewAdministrator() {
		return newAdministrator;
	}

	public void setNewAdministrator(Administrator newAdministrator) {
		this.newAdministrator = newAdministrator;
	}
}
