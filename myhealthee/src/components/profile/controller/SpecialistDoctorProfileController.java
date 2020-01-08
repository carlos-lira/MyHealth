package components.profile.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.profile.dao.ProfileFacadeRemote;
import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.Doctor;
import entity.imp.SpecialistDoctor;
import services.crud.Operation;
import utils.Messages;
import utils.SessionUtils;
import utils.StringUtils;

/**
 * Specialist doctor profile managed bean.
 * 
 * @author apeleteiro
 * @author adlo
 */
@Named("specialistDoctorProfile")
@SessionScoped
public class SpecialistDoctorProfileController implements Serializable {
	private static final long serialVersionUID = -4080395128447157836L;

	@EJB
	private ProfileFacadeRemote ejb;

	@EJB
	private SystemAdministrationFacadeRemote ejbSystemAdministration;

	/* Fields */
	private Operation mode;
	private SpecialistDoctor doctor;
	private List<SpecialistDoctor> listSpecialistDoctors;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.doctor = new SpecialistDoctor();
		this.listAllSpecialistDoctors();
	}
	
	/**
	 * @return the profile view page.
	 */
	public String redirectProfile() {
		this.doctor = (SpecialistDoctor) SessionUtils.getUser();
		return "profileView";
	}

	// MODAL ACTIONS
	public String openModal(Operation operation) {
		this.mode = operation;
		this.doctor = new SpecialistDoctor();
		return null;
	}

	public String openModal(Operation operation, SpecialistDoctor doctor) {
		this.mode = operation;
		this.doctor = doctor;
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	public String addSpecialistDoctor() {
		String password = doctor.getPassword();
		String repeatPassword = doctor.getRepeatPassword();
		if (!StringUtils.isSameString(password, repeatPassword)) {
			Messages.addWarnGlobalMessage("The passwords need to be equals");
		}
		if (!ejbSystemAdministration.addUser(doctor)) {
			Messages.addErrorGlobalMessage("Error adding the specialist doctor");
		}
		this.clear();
		this.listAllSpecialistDoctors();
		return null;
	}

	public String updateSpecialistDoctor() {
		String password = doctor.getPassword();
		if (password != null && !password.equals("")) {
			String repeatPassword = doctor.getRepeatPassword();
			if (!StringUtils.isSameString(password, repeatPassword)) {
				Messages.addWarnGlobalMessage("The passwords need to be equals");
			}
		}
		if (!ejbSystemAdministration.updateUser(doctor)) {
			Messages.addErrorGlobalMessage("Error updating the specialist doctor");
		}
		this.clear();
		this.listAllSpecialistDoctors();
		return null;
	}

	public String deleteSpecialistDoctor() {
		if (!ejbSystemAdministration.removeUser(this.doctor.getUsername())) {
			Messages.addErrorGlobalMessage("Error deleting the specialist doctor");
		}
		this.clear();
		this.listAllSpecialistDoctors();
		return null;
	}

	public String updateProfile() {
		String password = doctor.getPassword();
		if (password != null && !password.equals("")) {
			String repeatPassword = doctor.getRepeatPassword();
			if (!StringUtils.isSameString(password, repeatPassword)) {
				Messages.addWarnGlobalMessage("The passwords need to be equals");
			}
		}
		if (!ejbSystemAdministration.updateUser(doctor)) {
			Messages.addErrorGlobalMessage("Error updating the specialist doctor");
		}
		return null;
	}
	
	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.doctor = null;
	}

	private void listAllSpecialistDoctors() {
		this.listSpecialistDoctors = (List<SpecialistDoctor>) ejb.listAllSpecialistDoctors();
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public List<SpecialistDoctor> getListSpecialistDoctors() {
		return listSpecialistDoctors;
	}
}
