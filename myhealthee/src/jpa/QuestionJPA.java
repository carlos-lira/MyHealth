package jpa;
// Generated 28-nov-2019 14:02:34 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Question generated by hbm2java
 */
@Entity
@Table(name = "question", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "response"))
public class QuestionJPA implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double id;
	private PatientJPA patient;
	private String title;
	private String message;
	private long status;
	private String response;

	public QuestionJPA() {
	}

	public QuestionJPA(double id, PatientJPA patient, String title, String message, long status) {
		this.id = id;
		this.patient = patient;
		this.title = title;
		this.message = message;
		this.status = status;
	}

	public QuestionJPA(double id, PatientJPA patient, String title, String message, long status, String response) {
		this.id = id;
		this.patient = patient;
		this.title = title;
		this.message = message;
		this.status = status;
		this.response = response;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, precision = 17, scale = 17)
	public double getId() {
		return this.id;
	}

	public void setId(double id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false)
	public PatientJPA getPatient() {
		return this.patient;
	}

	public void setPatient(PatientJPA patient) {
		this.patient = patient;
	}

	@Column(name = "title", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "message", nullable = false, length = 2000)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "status", nullable = false)
	public long getStatus() {
		return this.status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	@Column(name = "response", unique = true, length = 2000)
	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
