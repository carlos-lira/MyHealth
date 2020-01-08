package entity.imp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import entity.BaseEntity;
import entity.enums.QuestionStatus;
import utils.QueryNames;

/**
 * Question entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "QUESTION")
@NamedQueries({ 
	@NamedQuery(name = QueryNames.GET_ALL_QUESTIONS, query = "FROM Question q"),
	@NamedQuery(name = QueryNames.GET_ALL_QUESTIONS_BY_STATUS, query = "FROM Question q WHERE q.status = :status AND q.patient = :patient"),
	@NamedQuery(name = QueryNames.GET_QUESTION_BY_ID, query = "FROM Question q WHERE q.id = :id") 
})
public class Question extends BaseEntity {
	private static final long serialVersionUID = -1369102340964131110L;

	@Column(name = "TITLE", nullable = false, length = 100)
	private String title;

	@Column(name = "MESSAGE", nullable = false, length = 2000)
	private String message;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private QuestionStatus status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PATIENT_ID", nullable = false)
	private Patient patient;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "RESPONSE_ID")
	private Response response;

	// Getters & Setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public QuestionStatus getStatus() {
		return status;
	}

	public void setStatus(QuestionStatus status) {
		this.status = status;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}
