package components.profile.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.profile.dao.ProfileFacadeRemote;
import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import services.crud.Operation;
import utils.StringUtils;

/**
 * Users managed bean.
 * 
 * @author apeleteiro
 * @author adlo
 */
@Named("users")
@SessionScoped
public class UsersController implements Serializable {
	private static final long serialVersionUID = 5049635530011409637L;

	@EJB
	private ProfileFacadeRemote ejb;

	@EJB
	private SystemAdministrationFacadeRemote ejbSystemAdministration;

	/* Fields */
	private Operation mode;
	private User user;
	private List<Patient> listPatients;
	private List<FamilyDoctor> listFamilyDoctors;
	private List<SpecialistDoctor> listSpecialistDoctors;
	private List<Administrator> listAdministrators;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.listAllPatients();
		this.listAllFamilyDoctors();
		this.listAllSpecialistDoctors();
		this.listAllAdministrators();
	}

	// ACTIONS
	public String addPatient() {
		this.mode = Operation.CREATE;
		this.user = new Patient();
		return null;
	}

	public String addFamilyDoctor() {
		this.mode = Operation.CREATE;
		this.user = new FamilyDoctor();
		return null;
	}

	public String addSpecialistDoctor() {
		this.mode = Operation.CREATE;
		this.user = new SpecialistDoctor();
		return null;
	}

	public String addAdministrator() {
		this.mode = Operation.CREATE;
		this.user = new Administrator();
		return null;
	}

	public String openModal(Operation operation, User user) {
		this.mode = operation;
		this.user = user;
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.user = null;
	}

	private void listAllPatients() {
		this.listPatients = (List<Patient>) ejb.listAllPatients();
	}

	private void listAllFamilyDoctors() {
		this.listFamilyDoctors = (List<FamilyDoctor>) ejb.listAllFamilyDoctors();
	}

	private void listAllSpecialistDoctors() {
		this.listSpecialistDoctors = (List<SpecialistDoctor>) ejb.listAllSpecialistDoctors();
	}

	private void listAllAdministrators() {
		this.listAdministrators = (List<Administrator>) ejb.listAllAdministrators();
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public User getUser() {
		return user;
	}

	public List<Patient> getListPatients() {
		return listPatients;
	}

	public List<FamilyDoctor> getListFamilyDoctors() {
		return listFamilyDoctors;
	}

	public List<SpecialistDoctor> getListSpecialistDoctors() {
		return listSpecialistDoctors;
	}

	public List<Administrator> getListAdministrators() {
		return listAdministrators;
	}

	/**
	 * @return the modal title based on the user and the operation.
	 */
	public String getModalTitle() {
		if (this.mode == Operation.NO_OPERATION) {
			return "";
		}
		StringBuilder stb = new StringBuilder();
		switch (this.mode) {
		case CREATE:
			stb.append("Add");
			break;
		case READ:
			stb.append("Show");
			break;
		case UPDATE:
			stb.append("Update");
			break;
		case DELETE:
			stb.append("Delete");
			break;
		default:
			// nop
		}
		stb.append(" ").append(StringUtils.splitCamelCase(this.user.getClass().getSimpleName()));
		return stb.toString();
	}
}
