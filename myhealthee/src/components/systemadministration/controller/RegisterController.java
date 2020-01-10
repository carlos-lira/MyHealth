package components.systemadministration.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.Doctor;
import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import services.i18n.I18n;
import utils.Messages;

/**
 * Register managed bean.
 * 
 * @author adlo
 */
@Named("register")
@RequestScoped
public class RegisterController implements Serializable {
	private static final long serialVersionUID = 9033960712363105349L;

	@EJB
	private SystemAdministrationFacadeRemote ejb;

	/* Fields */
	private Patient patient;
	private Doctor doctor;
	private Administrator administrator;

	@PostConstruct
	public void init() {
		this.patient = new Patient();
		this.doctor = new Doctor();
		this.administrator = new Administrator();
	}
	
	// ACTIONS
	/**
	 * Register patient Action.
	 */
	public String registerPatient() {
		return this.registerUser(patient, I18n.translate("systemAdministration.info.000002"));
	}

	/**
	 * Register Family Doctor Action.
	 */
	public String registerFamilyDoctor() {
		FamilyDoctor familyDoctor = new FamilyDoctor();
		familyDoctor.copy(doctor);
		return this.registerUser(familyDoctor, I18n.translate("systemAdministration.info.000003"));
	}

	/**
	 * Register Specialist Doctor Action.
	 */
	public String registerSpecialistDoctor() {
		SpecialistDoctor specialistDoctor = new SpecialistDoctor();
		specialistDoctor.copy(doctor);
		return this.registerUser(specialistDoctor, I18n.translate("systemAdministration.info.000004"));
	}

	/**
	 * Register Administrator Action.
	 */
	public String registerAdministrator() {
		return this.registerUser(administrator, I18n.translate("systemAdministration.info.000005"));
	}

	// PRIVATE METHODS
	private boolean isSamePassword(User user) {
		String password = user.getPassword();
        String repeatPassword = user.getRepeatPassword();
        return password != null && password.equals(repeatPassword);
	}

	private String registerUser(User u, String sucessfulMessage) {
		// Check if the password are the same
		if (!isSamePassword(u)) {
			Messages.addWarnGlobalMessage(I18n.translate("gobal.error.100000"));
			return null;
		}
		// Try to add the user.
		if (ejb.addUser(u)) {
			Messages.addInfoMessage(sucessfulMessage);
			return "loginView";
		}
		return null;
	}

	// Getters & Setters
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}
}
