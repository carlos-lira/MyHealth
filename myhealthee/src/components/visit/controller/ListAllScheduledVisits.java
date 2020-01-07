package components.visit.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.visit.dao.VisitFacadeRemote;
import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;
import utils.SessionUtils;

@Named("listallvisits")
@SessionScoped
public class ListAllScheduledVisits implements Serializable {

	private static final long serialVersionUID = 2086597743467313039L;

	@EJB
	private VisitFacadeRemote ejb;

	private long id;
	private Date date;
	private List<Visit> visits = new ArrayList<Visit>();

	public List<Visit> listAllScheduledVisits() {
		try {
			User u = SessionUtils.getUser();
			if (u.getClass() == Administrator.class)
				this.visits = ejb.listAllScheduledVisits();
			else if (u.getClass() == Patient.class)
				this.visits = ejb.listAllScheduledVisits((Patient) u);
			else if (u.getClass() == FamilyDoctor.class)
				this.visits = ejb.listAllScheduledVisits((FamilyDoctor) u, date);

			return visits;
		} catch (Exception e) {
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

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

}