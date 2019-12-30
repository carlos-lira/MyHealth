package visit.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import entity.Visit;
import visit.dao.VisitFacadeRemote;


@Named("listallvisits")
@ViewScoped
public class ListAllScheduledVisits implements Serializable {
	
	private static final long serialVersionUID = 2086597743467313039L;

	@EJB
	private VisitFacadeRemote ejb;
	
	private long id;
	private Date date;
	private Collection<Visit> visits = new ArrayList<Visit>();

	public Collection<Visit> listAllScheduledVisits()
	{
		try {
			return ejb.listAllScheduledVisits();
		} 
		catch(Exception e){
			return null;
		}
	}
	
	public Collection<Visit> listAllScheduledVisits(long id, int userType)
	{
		try {
			if (userType == 0) { //Paciente
				this.visits = ejb.listAllScheduledVisits(id);
			}
			else {
				this.visits = ejb.listAllScheduledVisits(id, date);
			}
			
			return visits;
		} 
		catch(Exception e){
			//System.out.println(e);
			return null;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Collection<Visit> getVisits() {
		return visits;
	}

	public void setVisits(Collection<Visit> visits) {
		this.visits = visits;
	}

	

}