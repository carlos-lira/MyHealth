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


@Named("updatevisit")
@RequestScoped
public class UpdateVisit implements Serializable {

	private static final long serialVersionUID = 4084705021270669517L;

	@EJB
	private VisitFacadeRemote ejb;
	
	private Visit visit;
	private long id;
	private long doctorId;
	private long patientId;
	private Date newDate;
	private Date newTime;
	private String observations;
	private String result;
	//0 patient, 1 doctor
	private int userType;
	
	public void getVisit(long id, int userType)
	{
		visit = new Visit();
		
		try {
			setVisit(ejb.getVisit(id));
			this.id = id;
			doctorId = visit.getDoctorId();
			patientId = visit.getPatientId();
			newDate = visit.getDate();
			newTime = visit.getDate();
			observations = visit.getObservations();
			result = visit.getResult();
			this.userType = userType;
			System.out.println(visit.getId());
			
		} 
		catch(Exception e){
			System.out.println("Error");
		}
	}

	
	public void updateVisitTime() throws Exception 
	{
		Date d = concatDateTime();
		System.out.println(d);
		try {
			ejb.updateVisit(id, d);
		} 
		catch(Exception e){
			System.out.println("Error");
		}
	}

	public void updateVisitResult() throws Exception 
	{
		try {
			System.out.println("id:" +id);
			ejb.addResultToVisit(id, result);
		} 
		catch(Exception e){
			System.out.println(e.toString());
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


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}
	
	public int getUserType() {
		return userType;
	}


	public void setUserType(int userType) {
		this.userType = userType;
	}

}