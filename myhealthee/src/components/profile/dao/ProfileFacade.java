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
import entity.imp.MedicalSpeciality;
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
	public Patient changeFamilyDoctor(String id, String familyDoctorId) {
		try {
			Patient patient = (Patient) this.getUser(id);
			FamilyDoctor familyDoctor = (FamilyDoctor) this.getUser(familyDoctorId);
			if (patient != null && familyDoctor != null) {
				familyDoctor.addPatient(patient);
				em.merge(familyDoctor);
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
	public FamilyDoctor changePrimaryHealthcareCenter(String id, long capId) {
		try {
			FamilyDoctor familyDoctor = (FamilyDoctor) this.getUser(id);
			PrimaryHealthCareCenter cap = em.find(PrimaryHealthCareCenter.class, capId);
			if (familyDoctor != null && cap != null) {
				cap.addFamilyDoctor(familyDoctor);
				em.merge(cap);
				em.flush();
				return familyDoctor;
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
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SpecialistDoctor changeMedicalSpecialty(String id, long medicalSpecialtyId) {
		try {
			SpecialistDoctor specialistDoctor = (SpecialistDoctor) this.getUser(id);
			MedicalSpeciality medicalSpecialty = em.find(MedicalSpeciality.class, medicalSpecialtyId);
			if (specialistDoctor != null && medicalSpecialty != null) {
				medicalSpecialty.addSpecialistDoctor(specialistDoctor);
				em.merge(medicalSpecialty);
				em.flush();
				return specialistDoctor;
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
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
