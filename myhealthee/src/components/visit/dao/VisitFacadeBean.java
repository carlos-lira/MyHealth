package components.visit.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.logging.impl.Log4jLogger;

import components.systemadministration.dao.SystemAdministrationFacade;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;
import services.i18n.I18n;
import utils.Messages;

/**
 * Visit EJB.
 * 
 * @author clira
 * @author adlo
 */
@Stateless
public class VisitFacadeBean implements VisitFacadeRemote {

	private static final Logger logger = Log4jLogger.getLogger(SystemAdministrationFacade.class);
	private static final int MINUTES_BETWEEN_VISITS = 15;

	// Persistence Unit Context
	@PersistenceContext(name = "myhealthee")
	private EntityManager entman;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addVisit(FamilyDoctor familyDoctor, Patient patient, Date date, String observations) {
		try {
			Visit visit = new Visit();
			visit.setFamilyDoctor(familyDoctor);
			visit.setPatient(patient);
			visit.setDate(date);
			visit.setObservations(observations);

			entman.persist(visit);
		} catch (Exception e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateVisit(long id, Date date) {
		try {
			Visit v = entman.find(Visit.class, id);
			if (v != null) {
				v.setDate(date);
				entman.persist(v);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeVisit(long id) {
		try {
			entman.createQuery("DELETE from Visit v WHERE v.id = ?1").setParameter(1, id).executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addResultToVisit(long id, String result) {
		try {
			Visit v = entman.find(Visit.class, id);
			if (v != null) {
				v.setResult(result);
				entman.persist(v);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			Messages.addErrorGlobalMessage(I18n.translate("gobal.error.000001"));
		}
	}

	@Override
	public List<Visit> listAllScheduledVisits() {
		try {
			List<Visit> visits = entman.createQuery("from Visit v ORDER BY v.date ASC").getResultList();
			return visits;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Visit> listAllScheduledVisits(Patient patient) {
		try {
			List<Visit> visits = entman.createQuery("from Visit v WHERE v.patient = ?1 ORDER BY v.date ASC")
					.setParameter(1, patient).getResultList();
			return visits;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Visit> listAllScheduledVisits(FamilyDoctor familyDoctor, Date date) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			Date nextDay = cal.getTime();

			List<Visit> visits = entman.createQuery(
					"from Visit v WHERE v.familyDoctor = ?1 AND v.date >= ?2 AND v.date < ?3 ORDER BY v.date ASC")
					.setParameter(1, familyDoctor).setParameter(2, date).setParameter(3, nextDay).getResultList();
			return visits;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Visit getVisit(long id) {
		try {
			Visit visit = (Visit) entman.createQuery("from Visit WHERE id = ?1").setParameter(1, id).getSingleResult();
			return visit;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public boolean visitAvailable(FamilyDoctor familyDoctor, Date visitTime) {
		boolean visitAvailable = false;
		
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(visitTime);
			c.add(Calendar.MINUTE, MINUTES_BETWEEN_VISITS);
			Date higherMargin = c.getTime();
			c.setTime(visitTime);
			c.add(Calendar.MINUTE, -MINUTES_BETWEEN_VISITS);
			Date lowerMargin = c.getTime();

			Collection<Visit> visits = entman
					.createQuery("from Visit v WHERE v.familyDoctor = ?1 AND v.date < ?2 AND v.date > ?3")
					.setParameter(1, familyDoctor).setParameter(2, higherMargin).setParameter(3, lowerMargin)
					.getResultList();
			if (visits.isEmpty()) {
				visitAvailable = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return visitAvailable;
	}

	@Override
	public boolean visitAvailable(long visitId, Date visitTime) {
		boolean visitAvailable = false;

		try {
			Calendar c = Calendar.getInstance();
			c.setTime(visitTime);
			c.add(Calendar.MINUTE, MINUTES_BETWEEN_VISITS);
			Date higherMargin = c.getTime();
			c.setTime(visitTime);
			c.add(Calendar.MINUTE, -MINUTES_BETWEEN_VISITS);
			Date lowerMargin = c.getTime();

			Visit visit = entman.find(Visit.class, visitId);

			Collection<Visit> visits = entman
					.createQuery("from Visit v WHERE v.familyDoctor = ?1 AND v.date < ?2 AND v.date > ?3")
					.setParameter(1, visit.getFamilyDoctor()).setParameter(2, higherMargin).setParameter(3, lowerMargin)
					.getResultList();

			visits.remove(visit);

			if (visits.isEmpty()) {
				visitAvailable = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return visitAvailable;
	}

	@Override
	public Date nextAvailableAppointment(FamilyDoctor familyDoctor, Date requestedDate) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(requestedDate);
			c.add(Calendar.MINUTE, -MINUTES_BETWEEN_VISITS);
			
			List<Visit> visits = entman
					.createQuery("from Visit v WHERE v.familyDoctor = ?1 AND v.date > ?2 ORDER BY v.date ASC")
					.setParameter(1, familyDoctor).setParameter(2, c.getTime()).getResultList();

			Date previousVisit = null;
			Iterator<Visit> it = visits.iterator();
			while (it.hasNext()) {
				Visit visit = it.next();
				// FirstIteration
				if (previousVisit == null)
					previousVisit = visit.getDate();
				else {
					// Adding 30min to the previous visit
					c.setTime(previousVisit);
					c.add(Calendar.MINUTE, 2 * MINUTES_BETWEEN_VISITS);
					Date comparableDate = c.getTime();

					// If there is more than 30min between visits, we can schedule a visit
					if (comparableDate.compareTo(visit.getDate()) <= 0)
						break;
					else
						previousVisit = visit.getDate();
				}
			}

			c.setTime(previousVisit);
			c.add(Calendar.MINUTE, MINUTES_BETWEEN_VISITS);
			return c.getTime();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

}
