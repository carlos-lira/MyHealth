package components.profile.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.profile.dao.ProfileFacadeRemote;
import entity.Doctor;
import entity.imp.Administrator;
import entity.imp.Patient;

@Named("profile")
@SessionScoped
public class ProfileController implements Serializable{
	private static final long serialVersionUID = 9211805378364561644L;
	
	@EJB
    private ProfileFacadeRemote ejb;

	/* Fields */
	private Patient patient;
	private Doctor doctor;
	private Administrator administrator;

	@PostConstruct
	public void init() {
		this.patient = new Patient();
		this.doctor = new Doctor();
		this.administrator = new Administrator();
	}


	/* Actions */

}
