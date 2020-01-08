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
import entity.imp.FamilyDoctor;
import entity.imp.PrimaryHealthCareCenter;
import services.crud.Operation;
import utils.Messages;
import utils.SessionUtils;
import utils.StringUtils;

/**
 * Family doctor profile managed bean.
 * 
 * @author apeleteiro
 * @author adlo
 */
@Named("familyDoctorProfile")
@SessionScoped
public class FamilyDoctorProfileController implements Serializable {
	private static final long serialVersionUID = -4424706303551131719L;

	@EJB
	private ProfileFacadeRemote ejb;

	@EJB
	private SystemAdministrationFacadeRemote ejbSystemAdministration;

	/* Fields */
	private Operation mode;
	private boolean changeCapModalOpen;
	private FamilyDoctor doctor;
	private List<FamilyDoctor> listFamilyDoctors;
	private List<PrimaryHealthCareCenter> listCAPs;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.doctor = new FamilyDoctor();
		this.listAllFamilyDoctors();
	}

	/**
	 * @return the profile view page.
	 */
	public String redirectProfile() {
		this.doctor = (FamilyDoctor) SessionUtils.getUser();
		this.listAllCAPs();
		return "profileView";
	}

	// MODAL ACTIONS
	public String openModal(Operation operation) {
		this.mode = operation;
		this.doctor = new FamilyDoctor();
		return null;
	}

	public String openModal(Operation operation, FamilyDoctor doctor) {
		this.mode = operation;
		this.doctor = doctor;
		return null;
	}

	public String openChangeCapModal(FamilyDoctor doctor) {
		this.changeCapModalOpen = true;
		this.doctor = doctor;
		this.listAllCAPs();
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	// FAMILY DOCTOR
	public String addFamilyDoctor() {
		String password = doctor.getPassword();
		String repeatPassword = doctor.getRepeatPassword();
		if (!StringUtils.isSameString(password, repeatPassword)) {
			Messages.addWarnGlobalMessage("The passwords need to be equals");
		}
		if (!ejbSystemAdministration.addUser(doctor)) {
			Messages.addErrorGlobalMessage("Error adding the family doctor");
		}
		this.clear();
		this.listAllFamilyDoctors();
		return null;
	}

	public String updateFamilyDoctor() {
		String password = doctor.getPassword();
		if (password != null && !password.equals("")) {
			String repeatPassword = doctor.getRepeatPassword();
			if (!StringUtils.isSameString(password, repeatPassword)) {
				Messages.addWarnGlobalMessage("The passwords need to be equals");
			}
		}
		if (!ejbSystemAdministration.updateUser(doctor)) {
			Messages.addErrorGlobalMessage("Error updating the family doctor");
		}
		this.clear();
		this.listAllFamilyDoctors();
		return null;
	}

	public String deleteFamilyDoctor() {
		if (!ejbSystemAdministration.removeUser(this.doctor.getUsername())) {
			Messages.addErrorGlobalMessage("Error deleting the family doctor");
		}
		this.clear();
		this.listAllFamilyDoctors();
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
			Messages.addErrorGlobalMessage("Error updating the family doctor");
		}
		return null;
	}

	public String changeCap(PrimaryHealthCareCenter cap) {
		FamilyDoctor updatedUser = ejb.changePrimaryHealthcareCenter(doctor.getUsername(), cap);
		if (updatedUser == null) {
			Messages.addErrorGlobalMessage("Error changing the family doctor");
		} else {
			doctor = updatedUser;
		}
		this.clear();
		this.listAllFamilyDoctors();
		return null;
	}
	
	public String changeCapProfile(PrimaryHealthCareCenter cap) {
		FamilyDoctor updatedUser = ejb.changePrimaryHealthcareCenter(doctor.getUsername(), cap);
		if (updatedUser == null) {
			Messages.addErrorGlobalMessage("Error changing the family doctor");
		} else {
			SessionUtils.addUser(updatedUser);
			doctor = updatedUser;
		}
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.changeCapModalOpen = false;
		this.doctor = null;
	}

	private void listAllFamilyDoctors() {
		this.listFamilyDoctors = (List<FamilyDoctor>) ejb.listAllFamilyDoctors();
	}

	private void listAllCAPs() {
		this.listCAPs = (List<PrimaryHealthCareCenter>) ejbSystemAdministration.listAllCAPs();
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public boolean isChangeCapModalOpen() {
		return changeCapModalOpen;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public List<FamilyDoctor> getListFamilyDoctors() {
		return listFamilyDoctors;
	}

	public List<PrimaryHealthCareCenter> getListCAPs() {
		return listCAPs;
	}
}