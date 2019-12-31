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
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;
import utils.SessionUtils;
import visit.dao.VisitFacadeRemote;


@Named("listallvisits")
@SessionScoped
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
			User u = SessionUtils.getUser();
			if (u.getClass() == Administrator.class)
				this.visits = ejb.listAllScheduledVisits();
			else if (u.getClass() == Patient.class)
				this.visits = ejb.listAllScheduledVisits((Patient)u);
			else if (u.getClass() == FamilyDoctor.class)
				this.visits = ejb.listAllScheduledVisits((FamilyDoctor)u, date);
			
			return visits;
		} 
		catch(Exception e){
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