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
import services.i18n.I18n;
import utils.Messages;
import utils.SessionUtils;

/**
 * Show visit managed bean.
 * 
 * @author clira
 * @author adlo
 */
@Named("updatevisit")
@SessionScoped
public class UpdateVisit implements Serializable {
	private static final long serialVersionUID = 4084705021270669517L;

	/* Constants */
	private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat SDF_TIME = new SimpleDateFormat("h:mm aa");

	@EJB
	private VisitFacadeRemote ejb;

	/* Fields */
	private Visit visit;
	private long id;
	private Date newDate;
	private Date newTime;
	private String result;

	// ACTIONS
	public String visitToUpdate(Visit visit) {
		this.visit = visit;
		this.id = visit.getId();
		this.newDate = visit.getDate();
		this.newTime = visit.getDate();
		this.result = visit.getResult();

		User u = SessionUtils.getUser();
		if (u instanceof FamilyDoctor) {
			return "visitUpdateResultView";
		} else {
			return "visitUpdateView";
		}
	}

	public String updateVisitTime() {
		User u = SessionUtils.getUser();
		Date d = concatDateTime();

		try {
			if (visitPriorToCurrentTime(d)) {
				Messages.addErrorGlobalMessage(I18n.translate("visit.error.000001"));
				return null;
			} else {
				if (ejb.visitAvailable(id, d)) {
					ejb.updateVisit(visit.getId(), d);
					if (u instanceof Administrator) {
						return "visitsView";
					} else {
						return "homeView";
					}
				} else {
					Date nextAppointment = ejb.nextAvailableAppointment(visit.getFamilyDoctor(), d);
					String dateToPrint = SDF_DATE.format(nextAppointment);
					String hourToPrint = SDF_TIME.format(nextAppointment);
					Messages.addInfoGlobalMessage(I18n.translate("visit.info.000001", visit.getFamilyDoctor().getSurnames()));
					Messages.addInfoGlobalMessage(I18n.translate("visit.info.000002", hourToPrint, dateToPrint));
					return null; // timeslot unavailable
				}
			}
		} catch (Exception e) {
			Messages.addErrorGlobalMessage(I18n.translate("visit.error.000003"));
			return null;
		}
	}

	public String updateVisitResult() {
		try {
			ejb.addResultToVisit(id, result);
			return "homeView";
		} catch (Exception e) {
			Messages.addErrorGlobalMessage(I18n.translate("visit.error.000004"));
			return null;
		}
	}

	// Private Methods
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

	// Getters & Setters
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