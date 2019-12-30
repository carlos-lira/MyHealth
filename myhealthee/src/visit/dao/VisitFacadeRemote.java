package visit.dao;

import java.util.Date;

import javax.ejb.Remote;
import java.util.Collection;

import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;

@Remote
public interface VisitFacadeRemote {

	public void addVisit(FamilyDoctor doc, Patient patient, Date date, String observations);
	public void updateVisit(long id, Date date);
	public void removeVisit(long id);
	public void addResultToVisit(long id, String result);
	public Collection<Visit> listAllScheduledVisits();
	public Collection<Visit> listAllScheduledVisits(long patientId);
	public Collection<Visit> listAllScheduledVisits(long doctorId, Date date);
	public boolean visitAvailable (long doctorId, Date visitTime);
	public boolean visitAvailable (long doctorId, Date visitTime, long visitId);
	public Visit getVisit(long id);
	public Patient getPatient(long patientId);
	public FamilyDoctor getPatientDoctor(long patientId);
}
