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
			Patient p = entman.find(Patient.class, patientId);
			@SuppressWarnings("unchecked")
			Collection<Visit> visits = entman.createQuery("from Visit v WHERE v.patient = ?1").setParameter(1, p).getResultList();
			return visits;
		}
		catch (Exception e)
		{
			//System.out.println(e);
			return null;
		}
	}
	
	public Collection<Visit> listAllScheduledVisits(long doctorId, Date date){
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			Date nextDay = cal.getTime();
			
			FamilyDoctor fd = entman.find(FamilyDoctor.class, doctorId);
			
			@SuppressWarnings("unchecked")
			Collection<Visit> visits = entman.createQuery("from Visit v WHERE v.familyDoctor = ?1 AND v.date >= ?2 AND v.date < ?3").setParameter(1, fd).setParameter(2, date).setParameter(3, nextDay).getResultList();
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

			FamilyDoctor fd = entman.find(FamilyDoctor.class, doctorId);
			
			@SuppressWarnings("unchecked")
			Collection<Visit> visits = entman.createQuery("from Visit v WHERE v.familyDoctor = ?1 AND v.date < ?2 AND v.date > ?3")
												.setParameter(1, fd)
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
	
	public boolean visitAvailable (long doctorId, Date visitTime, long visitId) {
		boolean visitAvailable = false;
		
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(visitTime);
			c.add(Calendar.MINUTE, MINUTES_BETWEEN_VISITS);
			Date higherMargin = c.getTime();
			c.setTime(visitTime);
			c.add(Calendar.MINUTE, -MINUTES_BETWEEN_VISITS);
			Date lowerMargin = c.getTime();

			Visit v = entman.find(Visit.class, visitId);
			
			@SuppressWarnings("unchecked")
			Collection<Visit> visits = entman.createQuery("from Visit v WHERE v.familyDoctor = ?1 AND v.date < ?2 AND v.date > ?3")
												.setParameter(1, v.getFamilyDoctor())
												.setParameter(2, higherMargin)
												.setParameter(3, lowerMargin)
												.getResultList();
			
			visits.remove(v);
				
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
	
	public Patient getPatient(long patientId) {
		Patient p = entman.find(Patient.class, patientId);
		return p;
	}
	
	public FamilyDoctor getPatientDoctor(long patientId) {
		Patient p = entman.find(Patient.class, patientId);
		return p.getFamilyDoctor();
	}

}
