package components.visit.dao;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Remote;

import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;

/**
 * Visit EJB remote.
 * 
 * @author clira
 * @author adlo
 */
@Remote
public interface VisitFacadeRemote {

	/**
	 * Add a visit.
	 * 
	 * @param doc          the family doctor
	 * @param patient      the patient
	 * @param date         the date of the visit
	 * @param observations the observations
	 */
	public void addVisit(FamilyDoctor doc, Patient patient, Date date, String observations);

	/**
	 * Update a visit.
	 * 
	 * @param id   the visit id
	 * @param date the new date of the visit.
	 */
	public void updateVisit(long id, Date date);

	/**
	 * Remove a visit.
	 * 
	 * @param id the id of the visit.
	 */
	public void removeVisit(long id);

	/**
	 * Add the result of a visit.
	 * 
	 * @param id     the visit id
	 * @param result the result of the visit
	 */
	public void addResultToVisit(long id, String result);

	/**
	 * @return a list with all the visits, null otherwise
	 */
	public Collection<Visit> listAllScheduledVisits();

	/**
	 * List all scheduled visits from a patient
	 * 
	 * @param patient the patient
	 * @return a list with all the visits, null otherwise
	 */
	public Collection<Visit> listAllScheduledVisits(Patient patient);

	/**
	 * List all scheduled visits from a family doctor in a specific date.
	 * 
	 * @param familyDoctor the family doctor
	 * @param date         the date to look for visits
	 * @return a list with all the visits, null otherwise
	 */
	public Collection<Visit> listAllScheduledVisits(FamilyDoctor familyDoctor, Date date);

	/**
	 * Get a visit.
	 * 
	 * @param id visit id.
	 * @return the visit, null otherwise.
	 */
	public Visit getVisit(long id);

	/**
	 * Check if a family doctor is available for a visit in a specific date.
	 * 
	 * @param familyDoctor the family doctor
	 * @param visitTime    the date of the visit.
	 * @return true if is available, false otherwise.
	 */
	public boolean visitAvailable(FamilyDoctor familyDoctor, Date visitTime);

	/**
	 * Check if a visit is available.
	 * 
	 * @param visitId   the visit id.
	 * @param visitTime the visit date.
	 * @return true if is available, false otherwise.
	 */
	public boolean visitAvailable(long visitId, Date visitTime);

	/**
	 * Get the next available appointment of a family doctor.
	 * 
	 * @param familyDoctor  the family doctor.
	 * @param requestedDate the requested date.
	 * @return the date of the next appointment.
	 */
	public Date nextAvailableAppointment(FamilyDoctor familyDoctor, Date requestedDate);
}
