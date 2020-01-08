package components.profile.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.profile.dao.ProfileFacadeRemote;
import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import services.crud.Operation;
import utils.Messages;
import utils.SessionUtils;
import utils.StringUtils;

/**
 * Patient profile managed bean.
 * 
 * @author apeleteiro
 * @author adlo
 */
@Named("patientProfile")
@SessionScoped
public class PatientProfileController implements Serializable {
	private static final long serialVersionUID = 4372291719076494604L;

	@EJB
	private ProfileFacadeRemote ejb;

	@EJB
	private SystemAdministrationFacadeRemote ejbSystemAdministration;

	/* Fields */
	private Operation mode;
	private boolean changeFamilyDoctorModalOpen;
	private Patient patient;
	private List<Patient> listPatients;
	private List<FamilyDoctor> listFamilyDoctors;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.patient = new Patient();
		this.listAllPatients();
	}

	/**
	 * @return the profile view page.
	 */
	public String redirectProfile() {
		this.patient = (Patient) SessionUtils.getUser();
		this.listAllFamilyDoctors();
		return "profileView";
	}

	// MODAL ACTIONS
	public String openModal(Operation operation) {
		this.mode = operation;
		this.patient = new Patient();
		return null;
	}

	public String openModal(Operation operation, Patient patient) {
		this.mode = operation;
		this.patient = patient;
		return null;
	}

	public String openChangeFamilyDoctorModal(Patient patient) {
		this.changeFamilyDoctorModalOpen = true;
		this.patient = patient;
		this.listAllFamilyDoctors();
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	public String addPatient() {
		String password = this.patient.getPassword();
		String repeatPassword = this.patient.getRepeatPassword();
		if (!StringUtils.isSameString(password, repeatPassword)) {
			Messages.addWarnGlobalMessage("The passwords need to be equals");
		}
		if (!ejbSystemAdministration.addUser(this.patient)) {
			Messages.addErrorGlobalMessage("Error adding the patient");
		}
		this.clear();
		this.listAllPatients();
		return null;
	}

	public String updatePatient() {
		String password = this.patient.getPassword();
		if (password != null && !password.equals("")) {
			String repeatPassword = this.patient.getRepeatPassword();
			if (!StringUtils.isSameString(password, repeatPassword)) {
				Messages.addWarnGlobalMessage("The passwords need to be equals");
			}
		}
		if (!ejbSystemAdministration.updateUser(this.patient)) {
			Messages.addErrorGlobalMessage("Error updating the patient");
		}
		this.clear();
		this.listAllPatients();
		return null;
	}

	public String deletePatient() {
		if (!ejbSystemAdministration.removeUser(this.patient.getUsername())) {
			Messages.addErrorGlobalMessage("Error deleting the patient");
		}
		this.clear();
		this.listAllPatients();
		return null;
	}

	public String updateProfile() {
		String password = this.patient.getPassword();
		if (password != null && !password.equals("")) {
			String repeatPassword = this.patient.getRepeatPassword();
			if (!StringUtils.isSameString(password, repeatPassword)) {
				Messages.addWarnGlobalMessage("The passwords need to be equals");
			}
		}
		if (!ejbSystemAdministration.updateUser(this.patient)) {
			Messages.addErrorGlobalMessage("Error updating the patient");
		}
		return null;
	}

	public String changeFamilyDoctor(FamilyDoctor doctor) {
		Patient updatedUser = ejb.changeFamilyDoctor(patient.getUsername(), doctor);
		if (updatedUser == null) {
			Messages.addErrorGlobalMessage("Error changing the family doctor");
		} else {
			patient = updatedUser;
		}
		this.clear();
		this.listAllPatients();
		return null;
	}
	
	public String changeFamilyDoctorProfile(FamilyDoctor doctor) {
		Patient updatedUser = ejb.changeFamilyDoctor(patient.getUsername(), doctor);
		if (updatedUser == null) {
			Messages.addErrorGlobalMessage("Error changing the family doctor");
		} else {
			patient = updatedUser;
		}
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.changeFamilyDoctorModalOpen = false;
		this.patient = null;
	}

	private void listAllPatients() {
		this.listPatients = (List<Patient>) ejb.listAllPatients();
	}

	private void listAllFamilyDoctors() {
		this.listFamilyDoctors = (List<FamilyDoctor>) ejb.listAllFamilyDoctors();
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public boolean isChangeFamilyDoctorModalOpen() {
		return changeFamilyDoctorModalOpen;
	}

	public Patient getPatient() {
		return patient;
	}

	public List<Patient> getListPatients() {
		return listPatients;
	}

	public List<FamilyDoctor> getListFamilyDoctors() {
		return listFamilyDoctors;
	}
}
