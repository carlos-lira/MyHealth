package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Visit entity.
 * 
 * @author clira
 */
@Entity
@Table(name = "VISITS")
public class Visit implements Serializable {

	private static final long serialVersionUID = -7876193601884042332L;
	
	@Id
	@Column(name = "ID", nullable = false, unique = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "DOCTORID", nullable = false)
	private long doctorID;
	
	@Column(name = "PATIENTID", nullable = false)
	private long patientID;
	
	@Column(name = "DATE", nullable = false)
	private Date date;
	
	@Column(name = "OBSERVATIONS", length = 255, nullable = true, unique = false)
	private String observations;
	
	@Column(name = "RESULT", length = 255, nullable = true, unique = false)
	private String result;
	
	// Getters & Setters
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getDoctorId() {
		return doctorID;
	}
	public void setDoctorId(long doctorID) {
		this.doctorID = doctorID;
	}

	public long getPatientId() {
		return patientID;
	}
	public void setPatientId(long patientID) {
		this.patientID = patientID;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
}
