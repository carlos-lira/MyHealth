package systemadministration.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import systemadministration.dao.SystemAdministrationFacadeRemote;

/**
 * Cap managed bean
 * 
 * @author adlo
 */
@Named("cap")
@SessionScoped
public class CAPController implements Serializable {
	private static final long serialVersionUID = 9057739293599966002L;

	@EJB
	private SystemAdministrationFacadeRemote ejb;
	
	/* Fields */
	
	
	// ACTIONS
	public void listCAPs() {
		
	}
	
	// Getters & Setters
}
