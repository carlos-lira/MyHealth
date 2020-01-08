package components.visit.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.visit.dao.VisitFacadeRemote;
import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Visit;
import utils.Messages;
import utils.SessionUtils;

@Named("updatevisit")
@SessionScoped
public class UpdateVisit implements Serializable {

	private static final long serialVersionUID = 4084705021270669517L;

	@EJB
	private VisitFacadeRemote ejb;

	private Visit visit;
	private long id;
	private Date newDate;
	private Date newTime;
	private String result;

	private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat SDF_TIME = new SimpleDateFormat("h:mm aa");

	public String visitToUpdate(Visit visit) {
		this.visit = visit;
		this.id = visit.getId();
		this.newDate = visit.getDate();
		this.newTime = visit.getDate();
		this.result = visit.getResult();

		User u = SessionUtils.getUser();
		if (u.getClass() == FamilyDoctor.class)
			return "visitUpdateResultView";
		else
			return "visitUpdateView";
	}

	public String updateVisitTime() {
		User u = SessionUtils.getUser();
		Date d = concatDateTime();

		try {
			if (visitPriorToCurrentTime(d)) {
				Messages.addErrorGlobalMessage(
						"La visita que desea reservar ya ha pasado. Por favor introduzca una fecha posterior.");
				return null;
			} else {
				if (ejb.visitAvailable(id, d)) {
					ejb.updateVisit(visit.getId(), d);

					if (u.getClass() == Administrator.class)
						return "visitsView";
					else
						return "homeView";
				} else {
					Date nextAppointment = ejb.nextAvailableAppointment(visit.getFamilyDoctor(), d);
					String dateToPrint = SDF_DATE.format(nextAppointment);
					String hourToPrint = SDF_TIME.format(nextAppointment);
					Messages.addInfoGlobalMessage("El doctor " + visit.getFamilyDoctor().getSurnames()
							+ " no tiene visita disponible a esa hora.");
					Messages.addInfoGlobalMessage(
							"La siguiente hora disponible es a las " + hourToPrint + " el dia " + dateToPrint);
					return null; // timeslot unavailable
				}
			}
		} catch (Exception e) {
			Messages.addErrorGlobalMessage("A ocurrido un error modificando la hora.");
			return null;
		}
	}

	public String updateVisitResult() {
		try {
			// System.out.println("id:" +id);
			ejb.addResultToVisit(id, result);
			return "homeView";
		} catch (Exception e) {
			Messages.addErrorGlobalMessage("A ocurrido un error insertando el resultado.");
			return null;
		}
	}

	private Date concatDateTime() {
		LocalDate d = newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalTime t = newTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		LocalDateTime dt = LocalDateTime.of(d, t);

		Date newDate = Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
		return newDate;
	}

	private boolean visitPriorToCurrentTime(Date d) {
		return ((d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()).compareTo(LocalDateTime.now()) < 0);
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}

	public Date getNewTime() {
		return newTime;
	}

	public void setNewTime(Date newTime) {
		this.newTime = newTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}