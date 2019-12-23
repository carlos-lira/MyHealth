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
import javax.inject.Named;

import entity.Visit;
import visit.dao.VisitFacadeRemote;


@Named("showvisit")
@RequestScoped
public class ShowVisit implements Serializable {

	private static final long serialVersionUID = 6972663073709391508L;

	@EJB
	private VisitFacadeRemote ejb;
	
	private Visit visit = new Visit();

	public void showVisit(long id)
	{
		try {
			setVisit(ejb.getVisit(id));
		} 
		catch(Exception e){
			System.out.println("Error");
		}
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

}