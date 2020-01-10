package components.profile.dao;

import java.util.Collection;

import javax.ejb.Remote;

import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
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
	 * @param familyDoctorId the if of the new family doctor of the patient.
	 * @return the updated user, null otherwise.
	 */
	public Patient changeFamilyDoctor(String id, String familyDoctorId);

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
	 * @param capId the id of the new cap of the family doctor.
	 * @return the updated user, null otherwise.
	 */
	public FamilyDoctor changePrimaryHealthcareCenter(String id, long capId);

	// SPECIALIST DOCTOR
	/**
	 * @return a list with all the specialist doctors, or an empty list if the
	 *         database doesn't have any row.
	 */
	public Collection<SpecialistDoctor> listAllSpecialistDoctors();
	
	/**
	 * Change the medical specialty of a specialist doctor.
	 * 
	 * @param id the specialist doctor id.
	 * @param medicalSpecialtyId the if of the new medical specialty of the doctor.
	 * @return the updated user, null otherwise.
	 */
	public SpecialistDoctor changeMedicalSpecialty(String id, long medicalSpecialtyId); 

	// ADMINISTRATOR
	/**
	 * @return a list with all the administrators, or an empty list if the database
	 *         doesn't have any row.
	 */
	public Collection<Administrator> listAllAdministrators();
}
