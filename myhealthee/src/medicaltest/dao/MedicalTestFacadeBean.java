package medicaltest.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.logging.impl.Log4jLogger;

import components.systemadministration.dao.SystemAdministrationFacade;

import java.util.ArrayList;

import entity.User;
import entity.enums.QuestionStatus;
import entity.enums.TestType;
import entity.imp.Image;
import entity.imp.MedicalTest;
import entity.imp.Patient;
import entity.imp.Question;
import entity.imp.Response;
import entity.imp.SpecialistDoctor;
import utils.SessionUtils;


@Stateless
public class MedicalTestFacadeBean implements MedicalTestFacadeRemote {
	
	private static final Logger logger = Log4jLogger.getLogger(SystemAdministrationFacade.class);
	final int MINUTES_BETWEEN_VISITS = 15;
	
	//Persistence Unit Context
	@PersistenceContext(name="myhealthee") 
	private EntityManager em;
	
	
	public void askQuestion(long id, String title, String message) {
		try {	
			
			Patient patient = (Patient) SessionUtils.getUser();
			
			Question question = new Question();
			question.setMessage(message);
			question.setStatus(QuestionStatus.PENDING);
			question.setTitle(title);
			question.setCreateDate(new Date());
			question.setPatient(patient);
			
			
			em.persist(question);
			em.flush();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public void answerQuestion(String question, String response) {
		
		try {
			Patient patient = (Patient) SessionUtils.getUser();
			
			Question questionObj = this.getQuestion(question);
			
			Response responseObj = new Response();
			responseObj.setMessage(question);
			
			questionObj.setResponse(responseObj);
			questionObj.setUpdateDate(new Date());
			
			em.persist(questionObj);
			em.flush();
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	// TODO
	@Override
	public List<Question> listAllPendingQuestions(long id) {
		try {
			return (List<Question>) em.createQuery("from Question q WHERE q.message = ?1").setParameter("1", id).getResultList();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<Question>();
	}

	@Override
	public void addMedicalTest(long id, Date date, TestType type, String observations) {
		
		try {
			MedicalTest medicalTest = new MedicalTest();
			medicalTest.setDate(date);
			medicalTest.setTestType(type);
			medicalTest.setCreateDate(new Date());
			medicalTest.setSpecialistDoctor((SpecialistDoctor) this.getUser(id));
			
			em.persist(medicalTest);
			em.flush();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

	@Override
	public MedicalTest getMedicalTest(long id) {
		try {
			return (MedicalTest) em.createQuery("from MedicalTest mt WHERE mt.id").getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return null;
	}

	@Override
	public Question getQuestion(String question) {
		try {
			return (Question) em.createQuery("from Question q WHERE q.title = ?question").setParameter("question", question).getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return null;
	}

	@Override
	public void addImage(long medicalTestId, Image image) {
		try {
			MedicalTest medicalTest = this.getMedicalTest(medicalTestId);
			medicalTest.setImage(image);
			
			em.persist(medicalTest);;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

	@Override
	public void updateImage(long medicalTestId, Image image) {
		try {
			MedicalTest medicalTest = this.getMedicalTest(medicalTestId);
			medicalTest.setImage(image);
			
			em.persist(medicalTest);;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void removeImage(long medicalTestId) {
		try {
			MedicalTest medicalTest = this.getMedicalTest(medicalTestId);
			medicalTest.setImage(null);
			
			em.persist(medicalTest);;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

	@Override
	public List<SpecialistDoctor> findSpecialistDoctorByMedicalSpeciality(String specialityName) {
		try {
			return (List<SpecialistDoctor>) em.createQuery("from SpecialistDoctor sd WHERE ms.medicalSpeciality.name = ?specialityName")
												.setParameter("specialityName", specialityName).getResultList();
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}		
		
		return new ArrayList<SpecialistDoctor>();
	}
	
	/*---------------- PRIVATE OPERATIONS --------------------*/
	
	private User getUser(long id) {
		try {
			return (User) em.createQuery("from User u WHERE u.id = ?1").setParameter("1", id).getSingleResult();
		} catch (NoResultException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
}
