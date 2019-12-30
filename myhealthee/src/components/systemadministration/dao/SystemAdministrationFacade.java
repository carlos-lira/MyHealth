package components.systemadministration.dao;

import java.util.ArrayList;
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
import entity.imp.FamilyDoctor;
import entity.imp.MedicalSpeciality;
import entity.imp.PrimaryHealthCareCenter;
import security.Cypher;
import security.HashAlgorithm;
import utils.Messages;
import utils.QueryNames;

/**
 * System administration EJB.
 * 
 * @author adlo
 */
@Stateless
public class SystemAdministrationFacade implements SystemAdministrationFacadeRemote {

	private static final Logger logger = Log4jLogger.getLogger(SystemAdministrationFacade.class);
	private static final HashAlgorithm algorithm = HashAlgorithm.MD5;
	
	@PersistenceContext(name = "myhealthee")
	private EntityManager em;

	@Override
	public User login(String id, String password) {
		logger.info("Try of loggin of user: " + id);
		User u = this.getUser(id);
		if (u != null && Cypher.verifyPassword(algorithm, password, u.getPassword())) {
			logger.info("User logged: " + id);
			return u;
		}
		return null;
	}

	@Override
	public void logout() {
		// nop
	}

	@Override
	public List<User> listAllUsers() {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_USERS).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<User>();
	}
	
	@Override
	public User getUser(String id) {
		try {
			return (User) em.createNamedQuery(QueryNames.GET_USER).setParameter("email", id)
					.setParameter("username", id).getSingleResult();
		} catch (NoResultException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean addUser(User user) {
		try {
			String rawPassword = user.getPassword();
			user.setPassword(Cypher.createHashedPassword(algorithm, rawPassword));
			em.persist(user);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean removeUser(User user) {
		try {
			em.remove(user);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean removeUser(String id) {
		return this.removeUser(this.getUser(id));
	}

	@Override
	public List<PrimaryHealthCareCenter> listAllCAPs() {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_PRIMARY_HELTHCARE_CENTERS).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<PrimaryHealthCareCenter>();
	}

	@Override
	public PrimaryHealthCareCenter getCAP(String name) {
		try {
			return (PrimaryHealthCareCenter) em.createNamedQuery(QueryNames.GET_PRIMARY_HEALTHCARE_CENTER_BY_NAME)
					.setParameter("name", name).getSingleResult();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addCAP(String name, String location) {
		try {
			PrimaryHealthCareCenter cap = this.getCAP(name);
			if (cap == null) {
				cap = new PrimaryHealthCareCenter();
				cap.setName(name);
				cap.setLocation(location);
				em.persist(cap);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCAP(String name, String location) {
		try {
			PrimaryHealthCareCenter cap = this.getCAP(name);
			if (cap != null) {
				cap.setLocation(location);
				em.merge(cap);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCAP(String name) {
		try {
			PrimaryHealthCareCenter cap = this.getCAP(name);
			if (cap != null) {
				em.remove(cap);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public List<FamilyDoctor> listAllFamilyDoctorsByCAP(PrimaryHealthCareCenter cap) {
		// TODO
		return null;
	}

	@Override
	public List<MedicalSpeciality> listAllMedicalSpecialities() {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_MEDICAL_SPECIALITIES).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<MedicalSpeciality>();
	}

	@Override
	public MedicalSpeciality getMedicalSpecialty(String name) {
		try {
			return (MedicalSpeciality) em.createNamedQuery(QueryNames.GET_MEDICAL_SPECIALITY_BY_NAME)
					.setParameter("name", name).getSingleResult();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addMedicalSpecialty(String name, String description) {
		try {
			MedicalSpeciality ms = this.getMedicalSpecialty(name);
			if (ms == null) {
				ms = new MedicalSpeciality();
				ms.setName(name);
				ms.setDescription(description);
				em.persist(ms);
				em.flush();
			} else {
				Messages.addErrorGlobalMessage("The medical speciality already exists");
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateMedicalSpecialty(String name, String description) {
		try {
			MedicalSpeciality ms = this.getMedicalSpecialty(name);
			if (ms != null) {
				ms.setDescription(description);
				em.merge(ms);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMedicalSpecialty(String name) {
		try {
			MedicalSpeciality ms = this.getMedicalSpecialty(name);
			if (ms != null) {
				em.remove(ms);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
	}
}
