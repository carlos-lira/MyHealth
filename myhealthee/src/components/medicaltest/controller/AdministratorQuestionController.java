package components.medicaltest.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.medicaltest.dao.MedicalTestFacadeRemote;
import entity.imp.Question;
import services.crud.Operation;

/**
 * Administrator question managed bean.
 * 
 * @author adlo
 */
@Named("administratorQuestion")
@SessionScoped
public class AdministratorQuestionController implements Serializable {
	private static final long serialVersionUID = 1812618401458540752L;

	@EJB
	private MedicalTestFacadeRemote ejb;

	/* Fields */
	private Operation mode;
	private Question question;
	private List<Question> listQuestions;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.listAllQuestions();
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

	public String deleteQuestion() {
		ejb.removeQuestion(question.getId());
		this.clear();
		this.listAllQuestions();
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.question = null;
	}

	private void listAllQuestions() {
		this.listQuestions = (List<Question>) ejb.listAllQuestions();
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public Question getQuestion() {
		return question;
	}

	public List<Question> getListQuestions() {
		return listQuestions;
	}
}
