package visit.dao;

import java.util.Date;

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
	
	//Persistence Unit Context
	@PersistenceContext(name="myhealthee") 
	private EntityManager entman;
	
	public void addVisit(long doctorID, long patientId, Date date, String observations) {
		Visit visit = new Visit();
		
		visit.setDoctorId(doctorID);
		visit.setPatientId(patientId);
		visit.setDate(date);
		visit.setObservations(observations);
		
		entman.persist(visit);
	}
	
	public void updateVisit(long id, Date date) {
		Visit v = entman.find(Visit.class, id);
	    if (v != null) {
	      v.setDate(date);
	      entman.persist(v);
	    }
	}
	
	public void removeVisit(long id) {
		entman.createQuery("DELETE from Visit b WHERE b.id = "+id).executeUpdate();
	}
	
	public void addResultToVisit(long id, String result) {
		Visit v = entman.find(Visit.class, id);
	    if (v != null) {
	      v.setResult(result);
	      entman.persist(v);
	    }
	}
	
	public Collection<Visit> listAllScheduledVisits(){
		@SuppressWarnings("unchecked")
		Collection<Visit> visits = entman.createQuery("from Visit").getResultList();
		return visits;
	}
	
	public Collection<Visit> listAllScheduledVisits(long id){
		Collection<Visit> visits = entman.createQuery("from Visit b WHERE b.patientID = ?1").setParameter(1, id).getResultList();
		return visits;
	}
	
	public Collection<Visit> listAllScheduledVisits(long id, Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date nextDay = cal.getTime();
		Collection<Visit> visits = entman.createQuery("from Visit b WHERE b.doctorID = ?1 AND b.date >= ?2 AND b.date < ?3").setParameter(1, id).setParameter(2, date).setParameter(3, nextDay).getResultList();
		return visits;
	}
	
	
	public Visit getVisit(long id){
		Visit visit = (Visit) entman.createQuery("from Visit WHERE id = ?1").setParameter(1, id).getSingleResult();
		return visit;
	}

}
