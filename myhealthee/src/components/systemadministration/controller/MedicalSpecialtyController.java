package components.systemadministration.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.imp.MedicalSpeciality;

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
	private MedicalSpeciality medicalSpeciality;
	private List<MedicalSpeciality> listMedicalSpecialty;

	@PostConstruct
	public void init() {
		this.medicalSpeciality = new MedicalSpeciality();
		this.listMedicalSpecialities();
	}

	// ACTIONS
	public void addMedicalSpeciality() {
		String name = this.medicalSpeciality.getName();
		String description = this.medicalSpeciality.getDescription();
		ejb.addMedicalSpecialty(name, description);
		this.listMedicalSpecialities(); // Not optimal the ejb should return the entity added.
	}
	
	public String showMedicalSpeciality() {
		// TODO
		return null;
	}

	public String updateMedicalSpeciality() {
		// TODO
		return null;
	}

	public String deleteMedicalSpeciality(final String name) {
		ejb.deleteMedicalSpecialty(name);
		this.listMedicalSpecialities();
		return null;
	}

	// PRIVATE METHODS
	private void listMedicalSpecialities() {
		this.listMedicalSpecialty = (List<MedicalSpeciality>) ejb.listAllMedicalSpecialities();
	}

	// Getters & Setters
	public MedicalSpeciality getMedicalSpeciality() {
		return medicalSpeciality;
	}

	public List<MedicalSpeciality> getListMedicalSpecialty() {
		return listMedicalSpecialty;
	}

}
