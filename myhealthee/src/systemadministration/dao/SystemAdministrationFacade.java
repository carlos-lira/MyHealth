package systemadministration.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.MedicalSpeciality;
import entity.imp.PrimaryHealthCareCenter;
import utils.PasswordUtils;
import utils.QueryNames;
import utils.SessionUtils;

/**
 * System administration EJB.
 * 
 * @author adlo
 */
@Stateless
public class SystemAdministrationFacade implements SystemAdministrationFacadeRemote {

	@PersistenceContext(name = "myhealthee")
	private EntityManager em;

	/**
	 * Get user by id
	 * 
	 * @param id the user email or username.
	 * @return the user if exists, null otherwise.
	 * @throws Exception
	 */
	public User getUser(String id) {
		try {
			return (User) em.createNamedQuery(QueryNames.GET_ADMINISTRATOR).setParameter("email", id).setParameter("username", id)
					.getSingleResult();
		} catch (NoResultException e) {
			// TODO log
		}
		return null;
	}

	@Override
	public User login(String id, String password) {
		User u = this.getUser(id);
		if (u != null && PasswordUtils.verifyPassword(password, u.getPassword())) {

			return u;
		}
		return null;
	}

	@Override
	public void logout() {
		// nop
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registerAdministrator(String email, String password) {
		User u = this.getUser(email);
		if (u == null) {
			u = new Administrator();
			u.setEmail(email);
			u.setPassword(PasswordUtils.createHashedPassword(password));
			em.persist(u);
			em.flush();
		}
	}

	@Override
	public List<PrimaryHealthCareCenter> listAllCAPs() {
		return em.createNamedQuery(QueryNames.GET_ALL_PRIMARY_HELTHCARE_CENTERS).getResultList();
	}

	@Override
	public PrimaryHealthCareCenter getCAP(String name) {
		return (PrimaryHealthCareCenter) em.createNamedQuery(QueryNames.GET_PRIMARY_HEALTHCARE_CENTER_BY_NAME)
				.setParameter("name", name).getSingleResult();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addCAP(String name, String location) {
		PrimaryHealthCareCenter cap = this.getCAP(name);
		if (cap == null) {
			cap = new PrimaryHealthCareCenter();
			cap.setName(name);
			cap.setLocation(location);
			em.persist(cap);
			em.flush();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCAP(String name, String location) {
		PrimaryHealthCareCenter cap = this.getCAP(name);
		if (cap != null) {
			cap.setLocation(location);
			em.merge(cap);
			em.flush();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCAP(String name) {
		PrimaryHealthCareCenter cap = this.getCAP(name);
		if (cap != null) {
			em.remove(cap);
			em.flush();
		}
	}

	@Override
	public Collection<FamilyDoctor> listAllFamilyDoctorsByCAP(PrimaryHealthCareCenter cap) {
		// TODO
		return null;
	}

	@Override
	public Collection<MedicalSpeciality> listAllMedicalSpecialities() {
		return em.createNamedQuery(QueryNames.GET_ALL_MEDICAL_SPECIALITIES).getResultList();
	}

	@Override
	public MedicalSpeciality getMedicalSpecialty(String name) {
		return (MedicalSpeciality) em.createNamedQuery(QueryNames.GET_MEDICAL_SPECIALITY_BY_NAME)
				.setParameter("name", name).getSingleResult();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addMedicalSpecialty(String name, String description) {
		MedicalSpeciality ms = this.getMedicalSpecialty(name);
		if (ms == null) {
			ms = new MedicalSpeciality();
			ms.setName(name);
			ms.setDescription(description);
			em.persist(ms);
			em.flush();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateMedicalSpecialty(String name, String description) {
		MedicalSpeciality ms = this.getMedicalSpecialty(name);
		if (ms != null) {
			ms.setDescription(description);
			em.merge(ms);
			em.flush();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMedicalSpecialty(String name) {
		MedicalSpeciality ms = this.getMedicalSpecialty(name);
		if (ms != null) {
			em.remove(ms);
			em.flush();
		}
	}
}
