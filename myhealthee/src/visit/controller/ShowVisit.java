package visit.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;
import utils.SessionUtils;
import visit.dao.VisitFacadeRemote;


@Named("showvisit")
@SessionScoped
public class ShowVisit implements Serializable {

	private static final long serialVersionUID = 6972663073709391508L;
	
	private Visit visit = new Visit();

	public String showVisit(Visit visit)
	{
		try {
			this.visit = visit;
			return "visitView";
		} 
		catch(Exception e){
			//System.out.println("Error");
			return null;
		}
	}

	public String backToVisits() {
		User u = SessionUtils.getUser();
		if (u.getClass() == Patient.class)
			return "allPatientVisitsView";
		else if (u.getClass() == FamilyDoctor.class)
			return "allScheduledVisitsView";
		else if (u.getClass() == Administrator.class)
			return "adminVisitsView";
		else
			return "visitDashboardView";			
	}
	
	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

}