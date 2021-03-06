package components.systemadministration.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.medicaltest.dao.MedicalTestFacadeRemote;
import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.imp.MedicalSpeciality;
import entity.imp.SpecialistDoctor;
import services.crud.Operation;

/**
 * Medical specialty bean
 * 
 * @author adlo
 */
@Named("medicalspec")
@SessionScoped
public class MedicalSpecialtyController implements Serializable {
	private static final long serialVersionUID = 8151170523040680234L;

	@EJB
	private SystemAdministrationFacadeRemote ejb;

	@EJB
	private MedicalTestFacadeRemote ejbMedicalTest;

	/* Fields */
	private Operation mode;
	private boolean specialistDoctorsByMedicalSpecialtyModalOpen;
	private MedicalSpeciality medicalSpeciality;
	private List<MedicalSpeciality> listMedicalSpecialty;
	private List<SpecialistDoctor> listSpecialistDoctorByMedicalSpecialty;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.medicalSpeciality = new MedicalSpeciality();
		this.listMedicalSpecialities();
	}

	// ACTIONS
	public String openModal(Operation operation) {
		this.mode = operation;
		this.medicalSpeciality = new MedicalSpeciality();
		return null;
	}

	public String openModal(Operation operation, MedicalSpeciality medicalSpeciality) {
		this.mode = operation;
		this.medicalSpeciality = medicalSpeciality;
		return null;
	}

	public String openSpecialistDoctorsByMedicalSpecialtyModal(MedicalSpeciality medicalSpeciality) {
		this.specialistDoctorsByMedicalSpecialtyModalOpen = true;
		this.listSpecialistDoctorsByMedicalSpeciality(medicalSpeciality);
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	public String addMedicalSpeciality() {
		String name = this.medicalSpeciality.getName();
		String description = this.medicalSpeciality.getDescription();
		this.clear();
		ejb.addMedicalSpecialty(name, description);
		this.listMedicalSpecialities();
		return null;
	}

	public String updateMedicalSpeciality() {
		String name = this.medicalSpeciality.getName();
		String description = this.medicalSpeciality.getDescription();
		this.clear();
		ejb.updateMedicalSpecialty(name, description);
		this.listMedicalSpecialities();
		return null;
	}

	public String deleteMedicalSpeciality() {
		String name = this.medicalSpeciality.getName();
		this.clear();
		ejb.deleteMedicalSpecialty(name);
		this.listMedicalSpecialities();
		return null;
	}

	// PRIVATE METHODS
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.specialistDoctorsByMedicalSpecialtyModalOpen = false;
		this.medicalSpeciality = null;
	}

	private void listMedicalSpecialities() {
		this.listMedicalSpecialty = (List<MedicalSpeciality>) ejb.listAllMedicalSpecialities();
	}

	private void listSpecialistDoctorsByMedicalSpeciality(MedicalSpeciality medicalSpeciality) {
		this.listSpecialistDoctorByMedicalSpecialty = (List<SpecialistDoctor>) ejbMedicalTest
				.findSpecialistByMedicalSpecialty(medicalSpeciality);
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public boolean isSpecialistDoctorsByMedicalSpecialtyModalOpen() {
		return specialistDoctorsByMedicalSpecialtyModalOpen;
	}

	public MedicalSpeciality getMedicalSpeciality() {
		return medicalSpeciality;
	}

	public List<MedicalSpeciality> getListMedicalSpecialty() {
		return listMedicalSpecialty;
	}

	public List<SpecialistDoctor> getListSpecialistDoctorByMedicalSpecialty() {
		return listSpecialistDoctorByMedicalSpecialty;
	}
}
