package visit.dao;

import java.util.Date;

import javax.ejb.Remote;
import java.util.Collection;
import entity.Visit;

@Remote
public interface VisitFacadeRemote {

	public void addVisit(long doctorId, long patientId, Date date, String observations);
	public void updateVisit(long id, Date date);
	public void removeVisit(long id);
	public void addResultToVisit(long id, String result);
	public Collection<Visit> listAllScheduledVisits();
	public Collection<Visit> listAllScheduledVisits(long id);
	public Collection<Visit> listAllScheduledVisits(long id, Date date);
	public Visit getVisit(long id);
}
