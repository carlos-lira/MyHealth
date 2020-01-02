package components.profile.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import entity.User;
import entity.imp.*;
import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.logging.impl.Log4jLogger;

import utils.QueryNames;

import java.util.List;

/**
 * Profile EJB.
 * 
 * @author apeleteiro
 */
@Stateless
public class ProfileFacade implements ProfileFacadeRemote {

	private static final Logger logger = Log4jLogger.getLogger(ProfileFacade.class);
	
	@PersistenceContext(name = "myhealthee")
	private EntityManager em;

	@Override
	public boolean changeFamilyDoctor(String id, FamilyDoctor newDoctor) {
		try {
			Patient patient = (Patient) this.getUser(id);
			if (patient != null) {
				patient.setFamilyDoctor(newDoctor);
				em.merge(patient);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean registerPatient(String id, String nif, String name, String surnames, String password, String email) {
		return false;
	}

	@Override
	public boolean registerSpecialistDoctor(String id, String nif, String name, String surnames, String password, String email, MedicalSpeciality speciality) {
		return false;
	}

	@Override
	public boolean registerFamilyDoctor(String id, String nif, String name, String surnames, String password, String email, PrimaryHealthCareCenter cap) {
		return false;
	}

	@Override
	public boolean updatePatientData(String id, String nif, String name, String surnames, String password, String email) {
		try {
			Patient patient = (Patient) this.getUser(id);
			if (patient != null) {
				patient.setNif(nif);
				patient.setName(name);
				patient.setSurnames(surnames);
				patient.setPassword(password);
				patient.setEmail(email);
				em.merge(patient);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean updateSpecialistDoctorData(String id, String nif, String name, String surnames, String password, String email, MedicalSpeciality speciality) {
		try {
			SpecialistDoctor specialistDoctor = (SpecialistDoctor) this.getUser(id);
			if (specialistDoctor != null) {
				specialistDoctor.setNif(nif);
				specialistDoctor.setName(name);
				specialistDoctor.setSurnames(surnames);
				specialistDoctor.setPassword(password);
				specialistDoctor.setEmail(email);
				specialistDoctor.setMedicalSpeciality(speciality);
				em.merge(specialistDoctor);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean updateFamilyDoctorData(String id, String nif, String name, String surnames, String password, String email, PrimaryHealthCareCenter cap) {
		try {
			FamilyDoctor familyDoctor = (FamilyDoctor) this.getUser(id);
			if (familyDoctor != null) {
				familyDoctor.setNif(nif);
				familyDoctor.setName(name);
				familyDoctor.setSurnames(surnames);
				familyDoctor.setPassword(password);
				familyDoctor.setEmail(email);
				familyDoctor.setPrimaryHealthcareCenter(cap);
				em.merge(familyDoctor);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean changePrimaryHealthCareCenter(String id, PrimaryHealthCareCenter newCenter) {
		try {
			FamilyDoctor familyDoctor = (FamilyDoctor) this.getUser(id);
			if (familyDoctor != null) {
				familyDoctor.setPrimaryHealthcareCenter(newCenter);
				em.merge(familyDoctor);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public User getUser(String email) {
		try {
			return (User) em.createQuery("from User WHERE email = ?1").setParameter(1, email).getSingleResult();
		} catch (NoResultException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public PrimaryHealthCareCenter getCap(String name) {
		try {
			return (PrimaryHealthCareCenter) em.createQuery("from PrimaryHealthCareCenter WHERE name = ?1").setParameter(1, name).getSingleResult();
		} catch (NoResultException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public List<FamilyDoctor> listAllFamilyDoctors() {
		try {
			return em.createQuery("from FamilyDoctor ORDER BY id ASC").getResultList();
		}
		catch (Exception e) {
			return null;
		}
	}

	public List<PrimaryHealthCareCenter> listAllCaps() {
		try {
			return em.createQuery("from PrimaryHealthCareCenter ORDER BY id ASC").getResultList();
		}
		catch (Exception e) {
			return null;
		}
	}
}
