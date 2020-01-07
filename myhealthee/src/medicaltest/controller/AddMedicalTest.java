package medicaltest.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import entity.enums.TestType;
import entity.imp.SpecialistDoctor;
import medicaltest.dao.MedicalTestFacadeRemote;
import utils.Messages;
import utils.SessionUtils;


@Named("addMedicalTest")
@RequestScoped
public class AddMedicalTest implements Serializable {

	private static final long serialVersionUID = 7142264007283605670L;
	
	@EJB
	private MedicalTestFacadeRemote ejb;
	
	private Date date;
	private Date time;
	private String observations;
	private TestType testType;

	
	public String addMedicalTest() {
		try {
			SpecialistDoctor doctor = (SpecialistDoctor) SessionUtils.getUser();
			Date d = concatDateTime();
			
			
			
			if (visitPriorToCurrentTime(d)) {
				Messages.addErrorGlobalMessage("La prueba médica que desea reservar ya ha pasado. Por favor introduzca una fecha posterior.");
				return null;
			}
			else {
				
				ejb.addMedicalTest(doctor.getId(), d, testType, observations);
				return "medicalTestDashboardView";
			}
		} 
		catch(Exception e){
			Messages.addErrorGlobalMessage("Usted no es un médico especialista y por tanto no puede asignar pruebas médicas.");
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
	
	private boolean visitPriorToCurrentTime(Date d) {
		return ((d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()).compareTo(LocalDateTime.now()) < 0 );
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
	
	public TestType getTestType() {
		return testType;
	}
	
	public void setTestType(TestType testType) {
		this.testType = testType;
	}
	
	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

}