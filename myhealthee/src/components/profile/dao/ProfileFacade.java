package components.profile.dao;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
import security.Cypher;
import security.HashAlgorithm;
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
	private static final HashAlgorithm algorithm = HashAlgorithm.MD5;

	@PersistenceContext(name = "myhealthee")
	private EntityManager em;

	// PATIENTS
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
	public boolean updatePatientData(Patient patient) {
		try {
			if (patient != null) {
				patient.setPassword(Cypher.createHashedPassword(algorithm, patient.getPassword()));
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
	public boolean changeFamilyDoctor(Patient patient, FamilyDoctor newDoctor) {
		try {
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

	// FAMILY DOCTORS
	@Override
	public Collection<FamilyDoctor> listAllFamilyDoctors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateFamilyDoctorData(FamilyDoctor doctor, PrimaryHealthCareCenter cap) {
		try {
			if (doctor != null) {
				doctor.setPassword(Cypher.createHashedPassword(algorithm, doctor.getPassword()));
				doctor.setPrimaryHealthcareCenter(cap);
				em.merge(doctor);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean changePrimaryHealthcareCenter(FamilyDoctor doctor, PrimaryHealthCareCenter cap) {
		// TODO Auto-generated method stub
		return false;
	}

	// SPECIALIST DOCTORS
	@Override
	public Collection<SpecialistDoctor> listAllSpecialistDoctors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateSpecialistDoctorData(SpecialistDoctor doctor, MedicalSpeciality speciality) {
		try {
			if (doctor != null) {
				doctor.setPassword(Cypher.createHashedPassword(algorithm, doctor.getPassword()));
				doctor.setMedicalSpeciality(speciality);
				em.merge(doctor);
				em.flush();
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	// ADMINISTRATORS
	@Override
	public Collection<Administrator> listAllAdministrators() {
		// TODO Auto-generated method stub
		return null;
	}
}
