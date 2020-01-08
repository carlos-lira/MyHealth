package medicaltest.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import entity.imp.FamilyDoctor;
import entity.imp.Question;
import medicaltest.dao.MedicalTestFacadeRemote;
import utils.SessionUtils;


@Named("pendingQuestions")
@RequestScoped
public class ListAllPendingQuestions implements Serializable {

	private static final long serialVersionUID = 7142264007283605670L;
	
	@EJB
	private MedicalTestFacadeRemote ejb;
	
	private List<Question> listQuestions;

	@PostConstruct
	public void init() {
		this.listAllPendingQuestions();
	}
	
//	public String listAllPendingQuestions() {
//		try {
//			
//			ejb.listAllPendingQuestions(doctor.getId());
//			return "medicalTestDashboardView";
//		} 
//		catch(Exception e){
//			Messages.addErrorGlobalMessage("Usted no es un médico especialista y por tanto no puede asignar pruebas médicas.");
//			return null;
//		}
//	}



	
	//Getters & Setters	
	
	public List<Question> getListQuestions() {
		return listQuestions;
	}


	/*---------------- PRIVATE OPERATIONS --------------------*/
	
	private void listAllPendingQuestions() {
		FamilyDoctor doctor = (FamilyDoctor) SessionUtils.getUser();
		this.listQuestions = (List<Question>) ejb.listAllPendingQuestions(doctor.getId());
	}

}