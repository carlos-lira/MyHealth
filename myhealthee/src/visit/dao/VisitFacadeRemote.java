package visit.dao;

import java.util.Date;
import java.util.List;

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
	public List<Visit> listAllScheduledVisits();
	public List<Visit> listAllScheduledVisits(Patient patient);
	public List<Visit> listAllScheduledVisits(FamilyDoctor familyDoctor, Date date);
	public Visit getVisit(long id);
	public boolean visitAvailable (FamilyDoctor familyDoctor, Date visitTime);
	public boolean visitAvailable (long visitId, Date visitTime);
	public Date nextAvailableAppointment(FamilyDoctor familyDoctor, Date requestedDate);
}
