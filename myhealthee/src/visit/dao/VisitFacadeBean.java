package visit.dao;

import java.util.Date;
import java.util.Iterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import entity.Visit;


@Stateless
public class VisitFacadeBean implements VisitFacadeRemote {
	
	final int MINUTES_BETWEEN_VISITS = 15;
	
	//Persistence Unit Context
	@PersistenceContext(name="myhealthee") 
	private EntityManager entman;
	
	public void addVisit(long doctorID, long patientId, Date date, String observations) {
		try {	
			Visit visit = new Visit();
			
			visit.setDoctorId(doctorID);
			visit.setPatientId(patientId);
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
			entman.createQuery("DELETE from Visit b WHERE b.id = ?1").setParameter(1, id).executeUpdate();
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
			Collection<Visit> visits = entman.createQuery("from Visit").getResultList();
			return visits;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public Collection<Visit> listAllScheduledVisits(long patientId){
		try {
			Collection<Visit> visits = entman.createQuery("from Visit b WHERE b.patientID = ?1").setParameter(1, patientId).getResultList();
			return visits;
		}
		catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public Collection<Visit> listAllScheduledVisits(long id, Date date){
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			Date nextDay = cal.getTime();
			Collection<Visit> visits = entman.createQuery("from Visit b WHERE b.doctorID = ?1 AND b.date >= ?2 AND b.date < ?3").setParameter(1, id).setParameter(2, date).setParameter(3, nextDay).getResultList();
			return visits;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	
	public boolean visitAvailable (long doctorId, Date visitTime) {
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
			Collection<Visit> visits = entman.createQuery("from Visit v WHERE v.doctorID = ?1 AND v.date < ?2 AND v.date > ?3 ")
												.setParameter(1, doctorId)
												.setParameter(2, higherMargin)
												.setParameter(3, lowerMargin)
												.getResultList();
			
			if (visits.isEmpty())
				visitAvailable = true;

			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		return visitAvailable;
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
	
	public long getPatientDoctor(long patientId) {
		long docId = -1;
		
		return docId;
	}

}
