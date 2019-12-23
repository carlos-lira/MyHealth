package components.profile.dao;

import entity.imp.FamilyDoctor;
import entity.imp.MedicalSpeciality;
import entity.User;
import entity.imp.PrimaryHealthCareCenter;

import javax.ejb.Remote;

/**
 * Profile EJB remote.
 * 
 * @author apeleteiro
 */
@Remote
public interface ProfileFacadeRemote {

	/**
	 *
	 * @param id Patient identification code
	 * @param newDoctor New family doctor
	 * @return true if family doctor change is done, false otherwise.
	 */
	public boolean changeFamilyDoctor(String id, FamilyDoctor newDoctor);

	/**
	 *
	 * @param id Patient identification code
	 * @param nif Patient NIF
	 * @param name Patient name
	 * @param surnames Patient surname
	 * @param password Patient password
	 * @param email Patient email
	 * @return true if patient is registered, false otherwise.
	 */
	public boolean registerPatient(String id, String nif, String name, String surnames, String password, String email);

	/**
	 *
	 * @param id Specialist doctor professional number
	 * @param nif Specialist doctor NIF
	 * @param name Specialist doctor name
	 * @param surnames Specialist doctor surname
	 * @param password Specialist doctor password
	 * @param email Specialist doctor email
	 * @param speciality Specialist doctor medical speciality
	 * @return true if specialist doctor is registered, false otherwise.
	 */
	public boolean registerSpecialistDoctor(String id, String nif, String name, String surnames, String password, String email, MedicalSpeciality speciality);

	/**
	 *
	 * @param id Family doctor professional number
	 * @param nif Family doctor NIF
	 * @param name Family doctor name
	 * @param surnames Family doctor surname
	 * @param password Family doctor password
	 * @param email Family doctor email
	 * @param cap Family doctor primary health care center
	 * @return true if family doctor is registered, false otherwise.
	 */
	public boolean registerFamilyDoctor(String id, String nif, String name, String surnames, String password, String email, PrimaryHealthCareCenter cap);

	/**
	 *
	 * @param id Patient identification code
	 * @param nif Patient NIF
	 * @param name Patient name
	 * @param surnames Patient surname
	 * @param password Patient password
	 * @param email Patient email
	 * @return true if patient data is updated, false otherwise.
	 */
	public boolean updatePatientData(String id, String nif, String name, String surnames, String password, String email);

	/**
	 *
	 * @param id Specialist doctor professional number
	 * @param nif Specialist doctor NIF
	 * @param name Specialist doctor name
	 * @param surnames Specialist doctor surname
	 * @param password Specialist doctor password
	 * @param email Specialist doctor email
	 * @param speciality Specialist doctor medical speciality
	 * @return true if specialist doctor data is updated, false otherwise.
	 */
	public boolean updateSpecialistDoctorData(String id, String nif, String name, String surnames, String password, String email, MedicalSpeciality speciality);

	/**
	 *
	 * @param id Family doctor professional number
	 * @param nif Family doctor NIF
	 * @param name Family doctor name
	 * @param surnames Family doctor surname
	 * @param password Family doctor password
	 * @param email Family doctor email
	 * @param cap Family doctor primary health care center
	 * @return true if family doctor data is updated, false otherwise.
	 */
	public boolean updateFamilyDoctorData(String id, String nif, String name, String surnames, String password, String email, PrimaryHealthCareCenter cap);

	/**
	 *
	 * @param id Family doctor professional number
	 * @param newCenter New primary health care center
	 * @return true if change is done, false otherwise.
	 */
	public boolean changePrimaryHealthCareCenter(String id, PrimaryHealthCareCenter newCenter);

	/**
	 *
	 * @param id User identification code
	 * @return a User with the specific identification code
	 */
	public User getUser(String id);

}
