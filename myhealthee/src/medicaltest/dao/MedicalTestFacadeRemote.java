package medicaltest.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;


import entity.enums.TestType;
import entity.imp.Image;
import entity.imp.MedicalTest;
import entity.imp.Question;
import entity.imp.SpecialistDoctor;

@Remote
public interface MedicalTestFacadeRemote {

	public void askQuestion(long id, String title, String message);
	public void answerQuestion(String question, String response);
	public List<Question> listAllPendingQuestions(long id);
	public void addMedicalTest(long id, Date date, TestType type, String observations);
	public MedicalTest getMedicalTest(long id);
	public Question getQuestion(String question);
	public void addImage(long medicalTestId, Image image);
	public void updateImage(long medicalTestId, Image image);
	public void removeImage(long medicalTestId);
	public List<SpecialistDoctor> findSpecialistDoctorByMedicalSpeciality (String specialityName);
}
