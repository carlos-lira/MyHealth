package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Inheritance(strategy = InheritanceType.JOINED)
public class Visit extends BaseEntity{

	private static final long serialVersionUID = -7876193601884042332L;
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
