package components.visit.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.visit.dao.VisitFacadeRemote;
import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;
import utils.SessionUtils;

/**
 * List all visit managed bean.
 * 
 * @author clira
 * @author adlo
 */
@Named("listallvisits")
@SessionScoped
public class ListAllScheduledVisits implements Serializable {
	private static final long serialVersionUID = 2086597743467313039L;

	@EJB
	private VisitFacadeRemote ejb;

	private long id;
	private Date date;
	private List<Visit> visits = new ArrayList<Visit>();

	// ACTIONS
	public List<Visit> listAllScheduledVisits() {
		try {
			User u = SessionUtils.getUser();
			if (u instanceof Administrator) {
				this.visits = (List<Visit>) ejb.listAllScheduledVisits();
			} else if (u instanceof Patient) {
				this.visits = (List<Visit>) ejb.listAllScheduledVisits((Patient) u);
			} else if (u instanceof FamilyDoctor) {
				this.visits = (List<Visit>) ejb.listAllScheduledVisits((FamilyDoctor) u, date);
			}
			return visits;
		} catch (Exception e) {
			return null;
		}
	}

	// Getters & Setters
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