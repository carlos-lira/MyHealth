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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import entity.BaseEntity;
import entity.enums.TestType;

/**
 * Medical Test entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "MEDICAL_TEST")
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

	@Column(name = "RESULT", length = 50)
	private String result;

	@Lob
	@Column(name = "IMAGE_ID")
	private Image image;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PATIENT_ID", nullable = false)
	private Patient patient;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SPECIALIST_DOCTOR_ID", nullable = false)
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
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
