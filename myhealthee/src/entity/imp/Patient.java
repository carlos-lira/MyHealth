package entity.imp;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import entity.Person;
import utils.QueryNames;

/**
 * Patient entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "PATIENT")
@NamedQueries({ 
	@NamedQuery(name = QueryNames.GET_ALL_PATIENTS, query = "FROM Patient u")
})
public class Patient extends Person {
	private static final long serialVersionUID = 5119359086731145181L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FAMILY_DOCTOR_ID")
	private FamilyDoctor familyDoctor;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIALIST_DOCTOR_ID")
	private List<SpecialistDoctor> specialistDoctors;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "PATIENT_ID")
	private List<MedicalTest> medicalTests;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "PATIENT_ID")
	private List<Visit> visits;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "PATIENT_ID")
	private List<Question> questions;

	// Getters & Setters
	public FamilyDoctor getFamilyDoctor() {
		return familyDoctor;
	}

	public void setFamilyDoctor(FamilyDoctor familyDoctor) {
		this.familyDoctor = familyDoctor;
	}

	public List<SpecialistDoctor> getSpecialistDoctors() {
		return specialistDoctors;
	}

	public void setSpecialistDoctors(List<SpecialistDoctor> specialistDoctors) {
		this.specialistDoctors = specialistDoctors;
	}

	public List<MedicalTest> getMedicalTests() {
		return medicalTests;
	}

	public void setMedicalTests(List<MedicalTest> medicalTests) {
		this.medicalTests = medicalTests;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
