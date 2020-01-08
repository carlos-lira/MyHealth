package components.profile.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.profile.dao.ProfileFacadeRemote;
import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.imp.MedicalSpeciality;
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
	private boolean changeMedicalSpecialtyModalOpen;
	private SpecialistDoctor doctor;
	private List<SpecialistDoctor> listSpecialistDoctors;
	private List<MedicalSpeciality> listMedicalSpecialties;

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
		this.listAllMedicalSpecialties();
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

	public String openChangeMedicalSpecialtyModal(SpecialistDoctor doctor) {
		this.changeMedicalSpecialtyModalOpen = true;
		this.doctor = doctor;
		this.listAllMedicalSpecialties();
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
			this.clear();
			return null;
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
				this.clear();
				return null;
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
				return null;
			}
		}
		if (!ejbSystemAdministration.updateUser(doctor)) {
			Messages.addErrorGlobalMessage("Error updating the specialist doctor");
		}
		return null;
	}

	public String changeMedicalSpecialty(MedicalSpeciality medicalSpecialty) {
		SpecialistDoctor updatedUser = ejb.changeMedicalSpecialty(doctor.getUsername(), medicalSpecialty.getId());
		if (updatedUser == null) {
			Messages.addErrorGlobalMessage("Error changing the family doctor");
		}
		this.clear();
		this.listAllSpecialistDoctors();
		return null;
	}

	public String changeMedicalSpecialtyProfile(MedicalSpeciality medicalSpecialty) {
		SpecialistDoctor updatedUser = ejb.changeMedicalSpecialty(doctor.getUsername(), medicalSpecialty.getId());
		if (updatedUser == null) {
			Messages.addErrorGlobalMessage("Error changing the medical specialty");
		} else {
			SessionUtils.addUser(updatedUser);
			doctor = updatedUser;
		}
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.changeMedicalSpecialtyModalOpen = false;
		this.doctor = null;
	}

	private void listAllSpecialistDoctors() {
		this.listSpecialistDoctors = (List<SpecialistDoctor>) ejb.listAllSpecialistDoctors();
	}

	private void listAllMedicalSpecialties() {
		this.listMedicalSpecialties = (List<MedicalSpeciality>) ejbSystemAdministration.listAllMedicalSpecialities();
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public boolean isChangeMedicalSpecialtyModalOpen() {
		return changeMedicalSpecialtyModalOpen;
	}

	public SpecialistDoctor getDoctor() {
		return doctor;
	}

	public List<SpecialistDoctor> getListSpecialistDoctors() {
		return listSpecialistDoctors;
	}

	public List<MedicalSpeciality> getListMedicalSpecialties() {
		return listMedicalSpecialties;
	}
}
