package components.medicaltest.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.medicaltest.dao.MedicalTestFacadeRemote;
import entity.imp.MedicalTest;
import services.crud.Operation;

/**
 * Administrator medical test managed bean.
 * 
 * @author adlo
 */
@Named("administratorMedicalTest")
@SessionScoped
public class AdministratorMedicalTestController implements Serializable {
	private static final long serialVersionUID = 659868834689516908L;

	@EJB
	private MedicalTestFacadeRemote ejb;

	/* Fields */
	private Operation mode;
	private MedicalTest medicalTest;
	private List<MedicalTest> listMedicalTests;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.listAllMedicalTests();
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

	public String deleteMedicalTest() {
		ejb.deleteMedicalTest(medicalTest.getId());
		this.clear();
		this.listAllMedicalTests();
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.medicalTest = null;
	}

	private void listAllMedicalTests() {
		this.listMedicalTests = (List<MedicalTest>) ejb.listAllMedicalTests();
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public MedicalTest getMedicalTest() {
		return medicalTest;
	}

	public List<MedicalTest> getListMedicalTests() {
		return listMedicalTests;
	}
}
