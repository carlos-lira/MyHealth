package entity.imp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import entity.Doctor;

/**
 * Specialist doctor entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "SPECIALIST_DOCTOR")
public class SpecialistDoctor extends Doctor {
	private static final long serialVersionUID = -7014944904910704221L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEDICAL_SPECIALITY_ID")
	private MedicalSpeciality medicalSpeciality;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PATIENT_ID")
	private List<Patient> patients = new ArrayList<Patient>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "SPECIALIST_DOCTOR_ID")
	private List<MedicalTest> medicalTests = new ArrayList<MedicalTest>();

	// Getters & Setters
	public MedicalSpeciality getMedicalSpeciality() {
		return medicalSpeciality;
	}

	public void setMedicalSpeciality(MedicalSpeciality medicalSpeciality) {
		this.medicalSpeciality = medicalSpeciality;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public List<MedicalTest> getMedicalTests() {
		return medicalTests;
	}

	public void setMedicalTests(List<MedicalTest> medicalTests) {
		this.medicalTests = medicalTests;
	}
}
