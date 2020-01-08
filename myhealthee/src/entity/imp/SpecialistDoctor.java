package entity.imp;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import entity.Doctor;
import utils.QueryNames;

/**
 * Specialist doctor entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "SPECIALIST_DOCTOR")
@NamedQueries({ 
	@NamedQuery(name = QueryNames.GET_ALL_SPECIALIST_DOCTORS, query = "FROM SpecialistDoctor u"),
	@NamedQuery(name = QueryNames.GET_ALL_SPECIALIST_DOCTORS_BY_MEDICAL_SPECIALTY, query = "FROM SpecialistDoctor u WHERE u.medicalSpeciality = :medicalSpeciality") 
})
public class SpecialistDoctor extends Doctor {
	private static final long serialVersionUID = -7014944904910704221L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEDICAL_SPECIALITY_ID")
	private MedicalSpeciality medicalSpeciality;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	@JoinColumn(name = "SPECIALIST_DOCTOR_ID")
	private List<MedicalTest> medicalTests;

	// Sync methods
	/**
	 * Add medical test
	 * 
	 * @param medicalTest the medical test
	 */
	public void addMedicalTest(MedicalTest medicalTest) {
		this.medicalTests.add(medicalTest);
		medicalTest.setSpecialistDoctor(this);
	}
	
	/**
	 * Remove medical test
	 * 
	 * @param medicalTest the medical test.
	 */
	public void removeMedicalTest(MedicalTest medicalTest) {
		medicalTest.setSpecialistDoctor(null);
		this.medicalTests.remove(medicalTest);
	}
	
	// Getters & Setters
	public MedicalSpeciality getMedicalSpeciality() {
		return medicalSpeciality;
	}

	public void setMedicalSpeciality(MedicalSpeciality medicalSpeciality) {
		this.medicalSpeciality = medicalSpeciality;
	}

	public List<MedicalTest> getMedicalTests() {
		return medicalTests;
	}

	public void setMedicalTests(List<MedicalTest> medicalTests) {
		this.medicalTests = medicalTests;
	}
}
