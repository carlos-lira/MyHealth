package components.medicaltest.dao;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
import entity.imp.FamilyDoctor;
import entity.imp.MedicalSpeciality;
import entity.imp.MedicalTest;
import entity.imp.Patient;
import entity.imp.Question;
import entity.imp.Response;
import entity.imp.SpecialistDoctor;
import services.i18n.I18n;
import utils.Messages;
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
	public Collection<Question> listAllQuestionsFromPatientsOfFamilyDoctor(String id, QuestionStatus status) {
		try {
			List<Question> list = new ArrayList<Question>();
			FamilyDoctor doctor = (FamilyDoctor) this.getUser(id);
			if (doctor != null) {
				Iterator<Patient> it = doctor.getPatients().iterator();
				while (it.hasNext()) {
					Patient p = it.next();
					List<Question> questions = (List<Question>) this.listAllQuestions(p.getUsername(), status);
					list.addAll(questions);
				}
			}
			return list;
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
				Question question = new Question();
				question.setTitle(title);
				question.setMessage(message);
				question.setStatus(QuestionStatus.PENDING);
				question.setPatient(patient);
				patient.addQuestion(question);
				em.persist(patient);
				em.flush();
			} else {
				Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000003"));
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
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
			} else {
				Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000004"));
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeQuestion(long id) {
		try {
			Question question = this.getQuestion(id);
			if (question != null) {
				question.getPatient().removeQuestion(question);
				em.remove(question);
				em.flush();
			} else {
				Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000004"));
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
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
	public Collection<MedicalTest> listAllMedicalTestsPatientByFamilyDoctor(String id) {
		try {
			List<MedicalTest> list = new ArrayList<MedicalTest>();
			FamilyDoctor doctor = (FamilyDoctor) this.getUser(id);
			if (doctor != null) {
				for (Patient p : doctor.getPatients()) {
					List<MedicalTest> l = p.getMedicalTests();
					if (l != null) {
						list.addAll(l);
					}
				}
			}
			return list;
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
			// Add the medical test
			Patient patient = (Patient) this.getUser(id);
			if (patient != null && medicalTest != null) {
				patient.addMedicalTest(medicalTest);
				em.merge(patient);
				em.flush();
			} else {
				Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000003"));
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMedicalTest(long id) {
		try {
			MedicalTest medicalTest = this.getMedicalTest(id);
			if (medicalTest != null) {
				medicalTest.getPatient().removeMedicalTest(medicalTest);
				medicalTest.getSpecialistDoctor().removeMedicalTest(medicalTest);
				em.remove(medicalTest);
				em.flush();
			} else {
				Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000006"));
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
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
			} else {
				Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000006"));
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
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
			} else {
				Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000006"));
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
		}
	}

	@Override
	public Collection<SpecialistDoctor> findSpecialistByMedicalSpecialty(MedicalSpeciality medicalSpecialty) {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_SPECIALIST_DOCTORS_BY_MEDICAL_SPECIALTY)
						.setParameter("medicalSpeciality", medicalSpecialty).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<SpecialistDoctor>();
	}
	
	@Override
	public String loadHighResImageBase64(long id) {
		try {
			MedicalTest medicalTest = em.find(MedicalTest.class, id);
			byte[] imageBytes = medicalTest.getHighResImage();
			if (imageBytes != null && imageBytes.length > 0) {
				return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
		}
		return null;
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
