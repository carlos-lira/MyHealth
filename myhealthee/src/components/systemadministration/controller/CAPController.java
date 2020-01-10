package components.systemadministration.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.imp.FamilyDoctor;
import entity.imp.PrimaryHealthCareCenter;
import services.crud.Operation;

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
	private Operation mode;
	private boolean familyDoctorsByCapModalOpen;
	private PrimaryHealthCareCenter cap;
	private List<PrimaryHealthCareCenter> listCaps;
	private List<FamilyDoctor> familyDoctorsByCap;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.cap = new PrimaryHealthCareCenter();
		this.listCAPs();
	}

	// ACTIONS
	public String openModal(Operation operation) {
		this.mode = operation;
		this.cap = new PrimaryHealthCareCenter();
		return null;
	}

	public String openModal(Operation operation, PrimaryHealthCareCenter cap) {
		this.mode = operation;
		this.cap = cap;
		return null;
	}
	
	public String openFamilyDoctorByCapModal(PrimaryHealthCareCenter cap) {
		this.familyDoctorsByCapModalOpen = true;
		this.listFamilyDoctorsByCap(cap);
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	public String addCAP() {
		String name = this.cap.getName();
		String location = this.cap.getLocation();
		this.clear();
		ejb.addCAP(name, location);
		this.listCAPs();
		return null;
	}

	public String updateCAP() {
		String name = this.cap.getName();
		String location = this.cap.getLocation();
		this.clear();
		ejb.updateCAP(name, location);
		this.listCAPs();
		return null;
	}

	public String deleteCAP() {
		String name = this.cap.getName();
		this.clear();
		ejb.deleteCAP(name);
		this.listCAPs();
		return null;
	}

	// PRIVATE METHODS
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.familyDoctorsByCapModalOpen = false;
		this.cap = null;
	}

	private void listCAPs() {
		this.listCaps = (List<PrimaryHealthCareCenter>) ejb.listAllCAPs();
	}
	
	private void listFamilyDoctorsByCap(PrimaryHealthCareCenter cap) {
		this.familyDoctorsByCap = (List<FamilyDoctor>) ejb.listAllFamilyDoctorsByCAP(cap);
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}
	
	public boolean isFamilyDoctorsByCapModal() {
		return familyDoctorsByCapModalOpen;
	}

	public PrimaryHealthCareCenter getCap() {
		return cap;
	}

	public List<PrimaryHealthCareCenter> getListCaps() {
		return listCaps;
	}

	public List<FamilyDoctor> getFamilyDoctorsByCap() {
		return familyDoctorsByCap;
	}
}
