package components.medicaltest.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.medicaltest.dao.MedicalTestFacadeRemote;
import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.imp.MedicalSpeciality;
import entity.imp.MedicalTest;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import services.crud.Operation;
import utils.SessionUtils;

/**
 * Patient medical test managed bean.
 * 
 * @author adlo
 */
@Named("patientMedicalTest")
@SessionScoped
public class PatientMedicalTestController implements Serializable {
	private static final long serialVersionUID = 5760920828057741890L;

	@EJB
	private MedicalTestFacadeRemote ejb;

	@EJB
	private SystemAdministrationFacadeRemote ejbSystemAdministration;

	/* Fields */
	private Operation mode;
	private Patient patient;
	private MedicalTest medicalTest;
	private List<MedicalTest> listMedicalTests;
	private List<MedicalSpeciality> listMedicalSpecialty;
	private List<SpecialistDoctor> listSpecialistDoctors;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.patient = (Patient) SessionUtils.getUser();
		this.listMedicalTests = this.patient.getMedicalTests();
		this.listAllMedicalSpecialties();
	}

	public String openModal(Operation operation, MedicalTest medicalTest) {
		this.mode = operation;
		this.medicalTest = medicalTest;
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.medicalTest = null;
	}

	public String searchSpecialistDoctor(MedicalSpeciality medicalSpeciality) {
		this.listSpecialistDoctors = (List<SpecialistDoctor>) ejb.findSpecialistByMedicalSpecialty(medicalSpeciality);
		return null;
	}

	private void listAllMedicalSpecialties() {
		this.listMedicalSpecialty = (List<MedicalSpeciality>) ejbSystemAdministration.listAllMedicalSpecialities();
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public Patient getPatient() {
		return patient;
	}

	public MedicalTest getMedicalTest() {
		return medicalTest;
	}

	public List<MedicalTest> getListMedicalTests() {
		return listMedicalTests;
	}

	public List<MedicalSpeciality> getListMedicalSpecialty() {
		return listMedicalSpecialty;
	}

	public List<SpecialistDoctor> getListSpecialistDoctors() {
		return listSpecialistDoctors;
	}
}
