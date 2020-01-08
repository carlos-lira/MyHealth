package components.medicaltest.dao;

import java.util.Collection;

import javax.ejb.Remote;

import entity.enums.QuestionStatus;
import entity.imp.MedicalSpeciality;
import entity.imp.MedicalTest;
import entity.imp.Question;
import entity.imp.SpecialistDoctor;

/**
 * Medical test EJB remote.
 * 
 * @author adlo
 */
@Remote
public interface MedicalTestFacadeRemote {

	// QUESTIONS & ANSWERS
	/**
	 * @return a list with all the questions, or an empty list if doesn't exist any.
	 */
	public Collection<Question> listAllQuestions();

	/**
	 * Get all the pending questions associated to a family doctor.
	 * 
	 * @param id     the patient identifier.
	 * @param status the status of the question.
	 * @return a list with all the pending questions, or an empty list if doesn't
	 *         exist any.
	 */
	public Collection<Question> listAllQuestions(String id, QuestionStatus status);

	/**
	 * Get all the questions from the patients of a family doctor.
	 * 
	 * @param id the family doctor id.
	 * @param status the status of the questions.
	 * @return  a list with all the pending questions, or an empty list if doesn't
	 *         exist any.
	 */
	public Collection<Question> listAllQuestionsFromPatientsOfFamilyDoctor(String id, QuestionStatus status);
	
	/**
	 * Get a question by id.
	 * 
	 * @param id the id of the question.
	 * @return the question if find it, null otherwise.
	 */
	public Question getQuestion(long id);

	/**
	 * Ask a question from a patient to his family doctor.
	 * 
	 * @param id      the patient identifier.
	 * @param title   the title of the question.
	 * @param message the body of the question.
	 */
	public void askQuestion(String id, String title, String message);

	/**
	 * Answer a question.
	 * 
	 * @param question the question.
	 * @param response the response message.
	 */
	public void answerQuestion(long id, String response);

	/**
	 * Remove a question.
	 * 
	 * @param id the id of the question.
	 */
	public void removeQuestion(long id);

	// MEDICAL TEST
	/**
	 * @return a list with all the medical test, or an empty list if doesn't exist
	 *         any.
	 */
	public Collection<MedicalTest> listAllMedicalTests();

	/**
	 * List all the medical tests from a specialist doctor.
	 * 
	 * @param id the specialist doctor identifier.
	 * @return a list with all the medical test associated to a specialist doctor,
	 *         or an empty list if doesn't exist any.
	 */
	public Collection<MedicalTest> listAllMedicalTestsBySpecialistDoctor(String id);
	
	/**
	 * List all the medical tests from the patients of a family doctor
	 * 
	 * @param id the family doctor id.
	 * @return a list with all the medical test associated to a patients of a familyDoctor,
	 *         or an empty list if doesn't exist any.
	 */
	public Collection<MedicalTest> listAllMedicalTestsPatientByFamilyDoctor(String id);

	/**
	 * Get a medical test by id.
	 * 
	 * @param id the id of the medical test.
	 * @return the medical test if find it, null otherwise.
	 */
	public MedicalTest getMedicalTest(long id);

	/**
	 * Add medical test.
	 * 
	 * @param id          the patient identifier.
	 * @param medicalTest the medical test to add.
	 * @return true if the medical test was added, false otherwise.
	 */
	public void addMedicalTest(String id, MedicalTest medicalTest);

	/**
	 * Remove a medical test.
	 * 
	 * @param id the medical test id.
	 */
	public void deleteMedicalTest(long id);
	
	// IMAGE
	/**
	 * Add a image to a medical test.
	 * 
	 * @param id    the id of the medical test.
	 * @param image an array of bytes tha represents the image.
	 */
	public void addImage(long id, byte[] image);

	/**
	 * Remove the image from the medical test.
	 * 
	 * @param id the id of the medical test.
	 */
	public void removeImage(long id);

	// FIND
	/**
	 * Find a specialist doctor y medical specialty name
	 * 
	 * @param medicalSpecialty the medical specialty
	 * @return a list with all the specialist doctor that have a medical specialty,
	 *         or an empty list if doesn't exist any.
	 */
	public Collection<SpecialistDoctor> findSpecialistByMedicalSpecialty(MedicalSpeciality medicalSpecialty);
}
