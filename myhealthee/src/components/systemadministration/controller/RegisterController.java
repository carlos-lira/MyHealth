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
		return this.registerUser(patient, "Patient register sucessfully");
	}

	/**
	 * Register Family Doctor Action.
	 */
	public String registerFamilyDoctor() {
		FamilyDoctor familyDoctor = new FamilyDoctor();
		familyDoctor.copy(doctor);
		return this.registerUser(familyDoctor, "Family Doctor register sucessfully");
	}

	/**
	 * Register Specialist Doctor Action.
	 */
	public String registerSpecialistDoctor() {
		SpecialistDoctor specialistDoctor = new SpecialistDoctor();
		specialistDoctor.copy(doctor);
		return this.registerUser(specialistDoctor, "Specialist Doctor register sucessfully");
	}

	/**
	 * Register Administrator Action.
	 */
	public String registerAdministrator() {
		return this.registerUser(administrator, "Administrator register sucessfully");
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
			Messages.addWarnGlobalMessage("The passwords need to be equals");
			return null;
		}
		// Try to add the user.
		if (ejb.addUser(u)) {
			Messages.addInfoMessage(sucessfulMessage);
			return "loginView";
		}
		Messages.addErrorGlobalMessage("The passwords need to be equals");
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
