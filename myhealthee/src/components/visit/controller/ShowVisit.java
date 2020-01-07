package components.visit.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entity.imp.Administrator;
import entity.imp.Visit;
import utils.SessionUtils;

@Named("showvisit")
@SessionScoped
public class ShowVisit implements Serializable {

	private static final long serialVersionUID = 6972663073709391508L;

	private Visit visit = new Visit();

	public String showVisit(Visit visit) {
		try {
			this.visit = visit;
			return "visitView";
		} catch (Exception e) {
			// System.out.println("Error");
			return null;
		}
	}

	public String backToVisits() {
		if (SessionUtils.getUser() instanceof Administrator) {
			return "visitsView";
		}
		return "homeView";
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

}