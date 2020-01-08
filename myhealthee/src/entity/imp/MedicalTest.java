package entity.imp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import entity.BaseEntity;
import entity.enums.TestType;
import utils.QueryNames;

/**
 * Medical Test entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "MEDICAL_TEST")
@NamedQueries({ 
	@NamedQuery(name = QueryNames.GET_ALL_MEDICAL_TESTS, query = "FROM MedicalTest mt"),
	@NamedQuery(name = QueryNames.GET_MEDICAL_TEST_BY_ID, query = "FROM MedicalTest mt WHERE mt.id = :id"),
	@NamedQuery(name = QueryNames.GET_ALL_MEDICAL_TESTS_BY_SPECIALIST_DOCTOR, query = "FROM MedicalTest mt WHERE mt.specialistDoctor = :specialistDoctor") 
})
public class MedicalTest extends BaseEntity {
	private static final long serialVersionUID = -3286906592775417950L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIME", nullable = false, length = 22)
	private Date time;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE", nullable = false, length = 22)
	private Date date;

	@Enumerated(EnumType.STRING)
	@Column(name = "TEST_TYPE", nullable = false)
	private TestType testType;

	@Column(name = "RESULT", nullable = false, length = 50)
	private String result;

	@Lob
	@Column(name = "HIGH_RES_IMAGE")
	private byte[] highResImage;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PATIENT_ID")
	private Patient patient;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SPECIALIST_DOCTOR_ID")
	private SpecialistDoctor specialistDoctor;

	// Getters & Setters
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TestType getTestType() {
		return testType;
	}

	public void setTestType(TestType testType) {
		this.testType = testType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public byte[] getHighResImage() {
		return highResImage;
	}

	public void setHighResImage(byte[] highResImage) {
		this.highResImage = highResImage;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public SpecialistDoctor getSpecialistDoctor() {
		return specialistDoctor;
	}

	public void setSpecialistDoctor(SpecialistDoctor specialistDoctor) {
		this.specialistDoctor = specialistDoctor;
	}
}
