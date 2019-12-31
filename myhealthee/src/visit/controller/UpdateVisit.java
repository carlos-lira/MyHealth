package visit.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entity.User;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;
import utils.Messages;
import utils.SessionUtils;
import visit.dao.VisitFacadeRemote;


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
	
	public String visitToUpdate(Visit visit) {
		this.visit = visit;
		this.id = visit.getId();
		this.newDate = visit.getDate();
		this.newTime = visit.getDate();
		this.result = visit.getResult();
		
		User u = SessionUtils.getUser();	
		if(u.getClass() == FamilyDoctor.class)
			return "updateVisitResultView";
		else
			return "updateVisitDateView";
	}

	public String updateVisitTime()
	{
		Date d = concatDateTime();
		try {
			//getVisit(id,0);
			if (ejb.visitAvailable(id, d)) {
				ejb.updateVisit(visit.getId(), d);
				return "visitDashboardView";
			}
			else {
				Date nextAppointment = ejb.nextAvailableAppointment(visit.getFamilyDoctor(), d);
				String dateToPrint = (new SimpleDateFormat("dd/MM/yyyy")).format(nextAppointment);
				String hourToPrint = (new SimpleDateFormat("HH:mm")).format(nextAppointment);
				Messages.addInfoGlobalMessage("El doctor " + visit.getFamilyDoctor().getSurnames() + " no tiene visita disponible a esa hora.");
				Messages.addInfoGlobalMessage("La siguiente hora disponible es a las " + hourToPrint + " el dia " + dateToPrint);
				return null; //timeslot unavailable
			}
		} 
		catch(Exception e){
			Messages.addErrorGlobalMessage("A ocurrido un error insertando el resultado.");
			return null;
		}
	}

	public String updateVisitResult()
	{
		try {
			//System.out.println("id:" +id);
			ejb.addResultToVisit(id, result);
			return "visitDashboardView";
		} 
		catch(Exception e){
			//System.out.println(e.toString());
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
	/*

	public FamilyDoctor getFamilyDoctor() {
		return familyDoctor;
	}

	public void setFamilyDoctor(FamilyDoctor familyDoctor) {
		this.familyDoctor = familyDoctor;
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setFamilyDoctor(Patient patient) {
		this.patient = patient;
	}
	
	public long getDoctorId() {
		return doctorId;
	}


	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}


	public long getPatientId() {
		return patientId;
	}


	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	 

	public String getObservations() {
		return observations;
	}


	public void setObservations(String observations) {
		this.observations = observations;
	}
	*/

	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}

}