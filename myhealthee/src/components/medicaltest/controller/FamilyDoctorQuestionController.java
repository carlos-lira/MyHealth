package components.medicaltest.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.medicaltest.dao.MedicalTestFacadeRemote;
import entity.enums.QuestionStatus;
import entity.imp.FamilyDoctor;
import entity.imp.Question;
import services.crud.Operation;
import utils.SessionUtils;

/**
 * Patient question managed bean.
 * 
 * @author adlo
 */
@Named("familyDoctorQuestion")
@SessionScoped
public class FamilyDoctorQuestionController implements Serializable {
	private static final long serialVersionUID = 1812618401458540752L;

	@EJB
	private MedicalTestFacadeRemote ejb;

	/* Fields */
	private Operation mode;
	private FamilyDoctor doctor;
	private Question question;
	private String response;
	private List<Question> listPendingQuestions;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.doctor = (FamilyDoctor) SessionUtils.getUser();
		this.question = new Question();
		this.listAllPendingQuestions();
	}

	// MODAL ACTIONS
	public String openModal(Operation operation, Question question) {
		this.mode = operation;
		this.question = question;
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	public String answerQuestion() {
		ejb.answerQuestion(question.getId(), response);
		this.clear();
		this.listAllPendingQuestions();
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.question = null;
		this.response = null;
	}

	private void listAllPendingQuestions() {
		this.listPendingQuestions = (List<Question>) ejb.listAllQuestionsFromPatientsOfFamilyDoctor(this.doctor.getUsername(), QuestionStatus.PENDING);
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public Question getQuestion() {
		return question;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public List<Question> getListPendingQuestions() {
		return listPendingQuestions;
	}
}