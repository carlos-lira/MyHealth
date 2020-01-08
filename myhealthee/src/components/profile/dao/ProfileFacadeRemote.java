package components.profile.dao;

import java.util.Collection;

import javax.ejb.Remote;

import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.PrimaryHealthCareCenter;
import entity.imp.SpecialistDoctor;

/**
 * Profile EJB remote.
 * 
 * @author apeleteiro
 * @author adlo
 */
@Remote
public interface ProfileFacadeRemote {

	// PATIENT
	/**
	 * @return a list with all the patients, or an empty list if the database
	 *         doesn't have any row.
	 */
	public Collection<Patient> listAllPatients();

	/**
	 * Change the family doctor of a patient.
	 * 
	 * @param id        the patient id.
	 * @param newDoctor the new family doctor of the patient.
	 * @return the updated user, null otherwise.
	 */
	public Patient changeFamilyDoctor(String id, FamilyDoctor newDoctor);

	// FAMILY DOCTOR
	/**
	 * @return a list with all the family doctors, or an empty list if the database
	 *         doesn't have any row.
	 */
	public Collection<FamilyDoctor> listAllFamilyDoctors();

	/**
	 * Change the cap of a family doctor.
	 * 
	 * @param id  the family doctor id.
	 * @param cap the new cap of the family doctor.
	 * @return the updated user, null otherwise.
	 */
	public FamilyDoctor changePrimaryHealthcareCenter(String id, PrimaryHealthCareCenter cap);

	// SPECIALIST DOCTOR
	/**
	 * @return a list with all the specialist doctors, or an empty list if the
	 *         database doesn't have any row.
	 */
	public Collection<SpecialistDoctor> listAllSpecialistDoctors();

	// ADMINISTRATOR
	/**
	 * @return a list with all the administrators, or an empty list if the database
	 *         doesn't have any row.
	 */
	public Collection<Administrator> listAllAdministrators();
}
