package components.medicaltest.dao;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.logging.impl.Log4jLogger;

import entity.User;
import entity.enums.QuestionStatus;
import entity.imp.MedicalTest;
import entity.imp.Patient;
import entity.imp.Question;
import entity.imp.Response;
import entity.imp.SpecialistDoctor;
import utils.QueryNames;

/**
 * System administration EJB.
 * 
 * @author adlo
 */
@Stateless
public class MedicalTestFacade implements MedicalTestFacadeRemote {

	private static final Logger logger = Log4jLogger.getLogger(MedicalTestFacade.class);

	@PersistenceContext(name = "myhealthee")
	private EntityManager em;

	@Override
	public Collection<Question> listAllQuestions() {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_QUESTIONS).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<Question>();
	}

	@Override
	public Collection<Question> listAllQuestions(String id, QuestionStatus status) {
		try {
			Patient patient = (Patient) this.getUser(id);
			if (patient != null) {
				return em.createNamedQuery(QueryNames.GET_ALL_QUESTIONS_BY_STATUS).setParameter("status", status)
						.setParameter("patient", patient).getResultList();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<Question>();
	}

	@Override
	public Question getQuestion(long id) {
		try {
			return (Question) em.createNamedQuery(QueryNames.GET_QUESTION_BY_ID).setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void askQuestion(String id, String title, String message) {
		try {
			Patient patient = (Patient) this.getUser(id);
			if (patient != null) {
				Question q = new Question();
				q.setTitle(title);
				q.setMessage(message);
				q.setStatus(QuestionStatus.PENDING);
				q.setPatient(patient);
				em.persist(q);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void answerQuestion(long id, String response) {
		try {
			Question q = this.getQuestion(id);
			if (q != null) {
				Response r = new Response();
				r.setMessage(response);
				q.setResponse(r);
				q.setStatus(QuestionStatus.ANSWERED);
				em.merge(q);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeQuestion(long id) {
		try {
			Question q = this.getQuestion(id);
			if (q != null) {
				em.remove(q);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public Collection<MedicalTest> listAllMedicalTests() {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_MEDICAL_TESTS).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<MedicalTest>();
	}

	@Override
	public Collection<MedicalTest> listAllMedicalTestsBySpecialistDoctor(String id) {
		try {
			SpecialistDoctor doctor = (SpecialistDoctor) this.getUser(id);
			if (doctor != null) {
				return em.createNamedQuery(QueryNames.GET_ALL_MEDICAL_TESTS_BY_SPECIALIST_DOCTOR)
						.setParameter("specialistDoctor", doctor).getResultList();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<MedicalTest>();
	}

	@Override
	public MedicalTest getMedicalTest(long id) {
		try {
			return (MedicalTest) em.createNamedQuery(QueryNames.GET_MEDICAL_TEST_BY_ID).setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addMedicalTest(String id, MedicalTest medicalTest) {
		try {
			// Test the date and time
			// TODO
			// Add the medical test
			Patient patient = (Patient) this.getUser(id);
			if (patient != null) {
				medicalTest.setPatient(patient);
				em.persist(medicalTest);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMedicalTest(long id) {
		try {
			MedicalTest medicalTest = this.getMedicalTest(id);
			if (medicalTest != null) {
				em.remove(medicalTest);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addImage(long id, byte[] image) {
		try {
			MedicalTest medicalTest = this.getMedicalTest(id);
			if (medicalTest != null) {
				medicalTest.setHighResImage(image);
				em.merge(medicalTest);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeImage(long id) {
		try {
			MedicalTest medicalTest = this.getMedicalTest(id);
			if (medicalTest != null) {
				medicalTest.setHighResImage(null);
				em.merge(medicalTest);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public Collection<SpecialistDoctor> findSpecialistByMedicalSpecialty(String name) {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_SPECIALIST_DOCTORS_BY_MEDICAL_SPECIALTY)
					.setParameter("medicalSpeciality", name).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<SpecialistDoctor>();
	}

	// Private Methods
	private User getUser(String id) {
		try {
			return (User) em.createNamedQuery(QueryNames.GET_USER).setParameter("email", id)
					.setParameter("username", id).getSingleResult();
		} catch (NoResultException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
