package components.medicaltest.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.medicaltest.dao.MedicalTestFacadeRemote;
import entity.imp.FamilyDoctor;
import entity.imp.MedicalTest;
import services.crud.Operation;
import utils.SessionUtils;

/**
 * Family doctor medical test managed bean.
 * 
 * @author adlo
 */
@Named("familyDoctorMedicalTest")
@SessionScoped
public class FamilyDoctorMedicalTestController implements Serializable {
	private static final long serialVersionUID = -8042972580408986656L;

	@EJB
	private MedicalTestFacadeRemote ejb;

	/* Fields */
	private Operation mode;
	private FamilyDoctor doctor;
	private MedicalTest medicalTest;
	private String highResImageBase64;
	private List<MedicalTest> listMedicalTests;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.doctor = (FamilyDoctor) SessionUtils.getUser();
		this.getAllMedicalTests();
	}

	public String openModal(Operation operation, MedicalTest medicalTest) {
		this.mode = operation;
		this.medicalTest = medicalTest;
		this.loadHighResImageBase64();
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

	private void getAllMedicalTests() {
		this.listMedicalTests = (List<MedicalTest>) ejb
				.listAllMedicalTestsPatientByFamilyDoctor(this.doctor.getUsername());
	}
	
	private void loadHighResImageBase64() {
		this.highResImageBase64 = ejb.loadHighResImageBase64(this.medicalTest.getId());
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public MedicalTest getMedicalTest() {
		return medicalTest;
	}
	
	public String getHighResImageBase64() {
		return highResImageBase64;
	}

	public List<MedicalTest> getListMedicalTests() {
		return listMedicalTests;
	}
}