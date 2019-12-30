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

import entity.User;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.Visit;
import visit.dao.VisitFacadeRemote;


@Named("addvisit")
@RequestScoped
public class AddVisit implements Serializable {

	private static final long serialVersionUID = 7142264007283605670L;
	
	@EJB
	private VisitFacadeRemote ejb;
	
	private long doctorId;
	private long patientId;
	private Date date;
	private Date time;
	private String observations;

	
	public void addVisit(long patientId) throws Exception 
	{
		try {
			//Get the patient's doctor
			FamilyDoctor doctor = ejb.getPatientDoctor(patientId);
			Patient patient = ejb.getPatient(patientId);
			
			if (ejb.visitAvailable(doctor.getId(), concatDateTime()))		
				//Add the visit
				ejb.addVisit(doctor, patient, concatDateTime(), observations);
			else
				System.out.println("Ha dado false"); //No visit available
		} 
		catch(Exception e){
			System.out.println(e);
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