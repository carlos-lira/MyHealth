package visit.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;

import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;


@Stateless
public class VisitFacadeBean implements VisitFacadeRemote {
	
	final int MINUTES_BETWEEN_VISITS = 15;
	
	//Persistence Unit Context
	@PersistenceContext(name="myhealthee") 
	private EntityManager entman;
	
	public void addVisit(FamilyDoctor familyDoctor, Patient patient, Date date, String observations) {
		try {	
			Visit visit = new Visit();
			visit.setFamilyDoctor(familyDoctor);
			visit.setPatient(patient);
			visit.setDate(date);
			visit.setObservations(observations);	
			
			entman.persist(visit);
		}
		catch (Exception e)
		{
			//
		}
	}
	
	public void updateVisit(long id, Date date) {
		try {
			Visit v = entman.find(Visit.class, id);
		    if (v != null) {
		      v.setDate(date);
		      entman.persist(v);
		    }
		}
		catch (Exception e)
		{
			//
		}
	}
	
	public void removeVisit(long id) {
		try {
			entman.createQuery("DELETE from Visit v WHERE v.id = ?1").setParameter(1, id).executeUpdate();
		}
		catch (Exception e)
		{
			//
		}
	}
	
	public void addResultToVisit(long id, String result) {
		try {
			Visit v = entman.find(Visit.class, id);
		    if (v != null) {
		      v.setResult(result);
		      entman.persist(v);
		    }
		}
		catch (Exception e)
		{
			//
		}
	}
	
	public Collection<Visit> listAllScheduledVisits(){
		try {
			@SuppressWarnings("unchecked")
			Collection<Visit> visits = entman.createQuery("from Visit v ORDER BY v.date ASC").getResultList();
			return visits;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public Collection<Visit> listAllScheduledVisits(Patient patient){
		try {
			@SuppressWarnings("unchecked")
			Collection<Visit> visits = entman.createQuery("from Visit v WHERE v.patient = ?1 ORDER BY v.date ASC").setParameter(1, patient).getResultList();
			return visits;
		}
		catch (Exception e)
		{
			//System.out.println(e);
			return null;
		}
	}
	
	public Collection<Visit> listAllScheduledVisits(FamilyDoctor familyDoctor, Date date){
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			Date nextDay = cal.getTime();
			
			@SuppressWarnings("unchecked")
			Collection<Visit> visits = entman.createQuery("from Visit v WHERE v.familyDoctor = ?1 AND v.date >= ?2 AND v.date < ?3 ORDER BY v.date ASC").setParameter(1, familyDoctor).setParameter(2, date).setParameter(3, nextDay).getResultList();
			return visits;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public Visit getVisit(long id){
		try {
			Visit visit = (Visit) entman.createQuery("from Visit WHERE id = ?1").setParameter(1, id).getSingleResult();
			return visit;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public boolean visitAvailable (FamilyDoctor familyDoctor, Date visitTime) {
		boolean visitAvailable = false;
		
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(visitTime);
			c.add(Calendar.MINUTE, MINUTES_BETWEEN_VISITS);
			Date higherMargin = c.getTime();
			c.setTime(visitTime);
			c.add(Calendar.MINUTE, -MINUTES_BETWEEN_VISITS);
			Date lowerMargin = c.getTime();
			
			@SuppressWarnings("unchecked")
			Collection<Visit> visits = entman.createQuery("from Visit v WHERE v.familyDoctor = ?1 AND v.date < ?2 AND v.date > ?3")
												.setParameter(1, familyDoctor)
												.setParameter(2, higherMargin)
												.setParameter(3, lowerMargin)
												.getResultList();
			if (visits.isEmpty())
				visitAvailable = true;		
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
		
		return visitAvailable;
	}
	
	public boolean visitAvailable (long visitId, Date visitTime) {
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
			
			@SuppressWarnings("unchecked")
			Collection<Visit> visits = entman.createQuery("from Visit v WHERE v.familyDoctor = ?1 AND v.date < ?2 AND v.date > ?3")
												.setParameter(1, visit.getFamilyDoctor())
												.setParameter(2, higherMargin)
												.setParameter(3, lowerMargin)
												.getResultList();
			
			visits.remove(visit);
				
			if (visits.isEmpty())
				visitAvailable = true;		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return visitAvailable;
	}
	
	public Date nextAvailableAppointment (FamilyDoctor familyDoctor, Date requestedDate) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(requestedDate);
			c.add(Calendar.MINUTE, -MINUTES_BETWEEN_VISITS);
			@SuppressWarnings("unchecked")
			Collection<Visit> visits = entman.createQuery("from Visit v WHERE v.familyDoctor = ?1 AND v.date > ?2 ORDER BY v.date ASC")
												.setParameter(1, familyDoctor)
												.setParameter(2, c.getTime())
												.getResultList();
			
			Date previousVisit = null;
			Iterator<Visit> it = visits.iterator();
			while(it.hasNext()) {
				Visit visit = it.next();
				//FirstIteration
				if (previousVisit == null)
					previousVisit = visit.getDate();
				else {
					//Adding 30min to the previous visit
					c.setTime(previousVisit);
					c.add(Calendar.MINUTE, 2*MINUTES_BETWEEN_VISITS);
					Date comparableDate = c.getTime();
					
					//If there is more than 30min between visits, we can schedule a visit
					if (comparableDate.compareTo(visit.getDate()) <= 0)
						break;
					else
						previousVisit = visit.getDate();
				}
			}
			
			c.setTime(previousVisit);
			c.add(Calendar.MINUTE, MINUTES_BETWEEN_VISITS);
			//To correct for CET
			c.add(Calendar.HOUR, -1);
			return c.getTime();
		}
		catch (Exception ex) {
			return null;
		}
	}
	
}
