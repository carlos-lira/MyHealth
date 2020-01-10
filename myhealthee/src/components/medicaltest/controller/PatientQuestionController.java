package components.medicaltest.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.medicaltest.dao.MedicalTestFacadeRemote;
import entity.enums.QuestionStatus;
import entity.imp.Patient;
import entity.imp.Question;
import services.crud.Operation;
import services.i18n.I18n;
import utils.Messages;
import utils.SessionUtils;

/**
 * Patient question managed bean.
 * 
 * @author adlo
 */
@Named("patientQuestion")
@SessionScoped
public class PatientQuestionController implements Serializable {
	private static final long serialVersionUID = 9011282058318022422L;

	@EJB
	private MedicalTestFacadeRemote ejb;

	/* Fields */
	private Operation mode;
	private Patient patient;
	private Question question;
	private List<Question> listPendingQuestions;
	private List<Question> listAnsweredQuestions;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.patient = (Patient) SessionUtils.getUser();
		this.question = new Question();
		this.listAllPendingQuestions();
		this.listAllAnsweredQuestions();
	}

	// MODAL ACTIONS
	public String openModal(Operation operation) {
		if (patient.getFamilyDoctor() == null) {
			Messages.addErrorGlobalMessage(I18n.translate("medicalTest.error.000001"));
			return null;
		}
		this.mode = operation;
		this.question = new Question();
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	public String askQuestion() {
		String title = question.getTitle();
		String message = question.getMessage();
		this.clear();
		ejb.askQuestion(patient.getUsername(), title, message);
		this.listAllPendingQuestions();
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.question = null;
	}

	private void listAllPendingQuestions() {
		this.listPendingQuestions = (List<Question>) ejb.listAllQuestions(patient.getUsername(),
				QuestionStatus.PENDING);
	}

	private void listAllAnsweredQuestions() {
		this.listAnsweredQuestions = (List<Question>) ejb.listAllQuestions(patient.getUsername(),
				QuestionStatus.ANSWERED);
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public Question getQuestion() {
		return question;
	}

	public List<Question> getListPendingQuestions() {
		return listPendingQuestions;
	}

	public List<Question> getListAnsweredQuestions() {
		return listAnsweredQuestions;
	}
}
