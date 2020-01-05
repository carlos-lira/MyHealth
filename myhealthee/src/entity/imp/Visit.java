package entity.imp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import entity.BaseEntity;

/**
 * Visit entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "VISIT")
public class Visit extends BaseEntity {
	private static final long serialVersionUID = -6835305679267047392L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE", nullable = false, length = 22)
	private Date date;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIME", nullable = false, length = 22)
	private Date time;

	@Column(name = "OBSERVATIONS", length = 2000)
	private String observations;

	@Column(name = "RESULT", length = 200)
	private String result;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FAMILY_DOCTOR_ID", nullable = false)
	private FamilyDoctor familyDoctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PATIENT_ID", nullable = false)
	private Patient patient;

	// Getters & Setters
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public FamilyDoctor getFamilyDoctor() {
		return familyDoctor;
	}

	public void setFamilyDoctor(FamilyDoctor familyDoctor) {
		this.familyDoctor = familyDoctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}
