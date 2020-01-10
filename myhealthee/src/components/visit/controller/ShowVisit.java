package components.visit.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entity.imp.Administrator;
import entity.imp.Visit;
import utils.SessionUtils;

/**
 * Show visit managed bean.
 * 
 * @author clira
 * @author adlo
 */
@Named("showvisit")
@SessionScoped
public class ShowVisit implements Serializable {
	private static final long serialVersionUID = 6972663073709391508L;

	/* Fields */
	private Visit visit = new Visit();

	// ACTIONS
	public String showVisit(Visit visit) {
		if (visit != null) {
			this.visit = visit;
			return "visitView";
		}
		return null;
	}

	public String backToVisits() {
		if (SessionUtils.getUser() instanceof Administrator) {
			return "visitsView";
		}
		return "homeView";
	}

	// Getters & Setters
	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

}