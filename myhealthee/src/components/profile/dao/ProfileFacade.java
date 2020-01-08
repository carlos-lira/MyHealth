package components.profile.dao;

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
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.PrimaryHealthCareCenter;
import entity.imp.SpecialistDoctor;
import utils.QueryNames;

/**
 * Profile EJB.
 * 
 * @author apeleteiro
 * @author adlo
 */
@Stateless
public class ProfileFacade implements ProfileFacadeRemote {

	private static final Logger logger = Log4jLogger.getLogger(ProfileFacade.class);

	@PersistenceContext(name = "myhealthee")
	private EntityManager em;

	@Override
	public Collection<Patient> listAllPatients() {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_PATIENTS).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<Patient>();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Patient changeFamilyDoctor(String id, FamilyDoctor newDoctor) {
		try {
			Patient patient = (Patient) this.getUser(id);
			if (patient != null) {
				patient.setFamilyDoctor(newDoctor);
				em.merge(patient);
				em.flush();
				return patient;
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Collection<FamilyDoctor> listAllFamilyDoctors() {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_FAMILY_DOCTORS).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<FamilyDoctor>();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FamilyDoctor changePrimaryHealthcareCenter(String id, PrimaryHealthCareCenter cap) {
		try {
			FamilyDoctor doctor = (FamilyDoctor) this.getUser(id);
			if (doctor != null) {
				doctor.setPrimaryHealthcareCenter(cap);
				em.merge(doctor);
				em.flush();
				return doctor;
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Collection<SpecialistDoctor> listAllSpecialistDoctors() {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_SPECIALIST_DOCTORS).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<SpecialistDoctor>();
	}

	@Override
	public Collection<Administrator> listAllAdministrators() {
		try {
			return em.createNamedQuery(QueryNames.GET_ALL_ADMINISTRATORS).getResultList();
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<Administrator>();
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
