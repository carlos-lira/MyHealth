package visit.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import entity.User;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;
import utils.Messages;
import utils.SessionUtils;
import visit.dao.VisitFacadeRemote;


@Named("addvisit")
@RequestScoped
public class AddVisit implements Serializable {

	private static final long serialVersionUID = 7142264007283605670L;
	
	@EJB
	private VisitFacadeRemote ejb;
	
	private Date date;
	private Date time;
	private String observations;

	private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm");
	
	public String addVisit() {
		try {
			//Get the patient's doctor
			Patient patient = (Patient) SessionUtils.getUser();
			FamilyDoctor doctor = patient.getFamilyDoctor();
			Date d = concatDateTime();
			
			if (ejb.visitAvailable(doctor, concatDateTime())){
				//Add the visit
				ejb.addVisit(doctor, patient, d, observations);
				return "visitDashboardView";
			}
			else {
				Date nextAppointment = ejb.nextAvailableAppointment(doctor, d);
				String dateToPrint = SDF_DATE.format(nextAppointment);
				String hourToPrint = SDF_TIME.format(nextAppointment);
				Messages.addInfoGlobalMessage("El doctor " + doctor.getSurnames() + " no tiene visita disponible a esa hora.");
				Messages.addInfoGlobalMessage("La siguiente hora disponible es a las " + hourToPrint + " el dia " + dateToPrint);
				return null;
			}
		} 
		catch(Exception e){
			Messages.addErrorGlobalMessage("No tiene un medico de cabecera asignado. Por favor, seleccione su m�dico de cabecera desde su perfil.");
			return null;
		}
	}

	private Date concatDateTime() {
		LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalTime t = time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		LocalDateTime dt = LocalDateTime.of(d, t);
		
		Date newDate = Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
		return newDate;
	}
	
	//Getters & Setters	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

}