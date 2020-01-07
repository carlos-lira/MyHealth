package medicaltest.controller;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import medicaltest.dao.MedicalTestFacadeRemote;
import utils.Messages;
import utils.SessionUtils;


@Named("askQuestion")
@RequestScoped
public class AskQuestion implements Serializable {

	private static final long serialVersionUID = 7142264007283605670L;
	
	@EJB
	private MedicalTestFacadeRemote ejb;
	
	
	private String title;
	private String message;

	
	public String askQuestion() {
		try {
			
			Patient patient = (Patient) SessionUtils.getUser();
			
			ejb.askQuestion(patient.getId(), title, message);
			return "medicalTestDashboardView";
			
		} 
		catch(Exception e){
			Messages.addErrorGlobalMessage("No tiene un medico de cabecera asignado. Por favor, seleccione su mdico de cabecera desde su perfil.");
			return null;
		}
	}

	
	//Getters & Setters	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
