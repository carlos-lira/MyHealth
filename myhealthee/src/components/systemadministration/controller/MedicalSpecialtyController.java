package components.systemadministration.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.systemadministration.dao.SystemAdministrationFacadeRemote;

/**
 * Medical specialty bean
 * 
 * @author adlo
 */
@Named("medicalspec")
@SessionScoped
public class MedicalSpecialtyController implements Serializable {
	private static final long serialVersionUID = 8151170523040680234L;

	@EJB
	private SystemAdministrationFacadeRemote ejb;
	
	/* Fields */
	
}
