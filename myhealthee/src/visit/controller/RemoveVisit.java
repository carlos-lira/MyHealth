package visit.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import entity.Visit;
import visit.dao.VisitFacadeRemote;


@Named("removevisit")
@RequestScoped
public class RemoveVisit implements Serializable {

	private static final long serialVersionUID = 7898448825101388023L;

	@EJB
	private VisitFacadeRemote ejb;

	public void removeVisit(long visitId) throws Exception 
	{
		try {
			ejb.removeVisit(visitId);
		} 
		catch(Exception e){
			System.out.println("Error");
		}
	}


}