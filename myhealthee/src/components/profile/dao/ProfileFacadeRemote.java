package components.profile.dao;

import java.util.Collection;

import javax.ejb.Remote;

import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.MedicalSpeciality;
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
	 * Update the patient data.
	 * 
	 * @param patient to be updated.
	 * @return true if the patient was updated, false otherwise.
	 */
	public boolean updatePatientData(Patient patient);

	/**
	 * Change the family doctor of a patient.
	 * 
	 * @param patient   the patient to change the family doctor.
	 * @param newDoctor the new family doctor of the patient.
	 * @return true if family doctor change is done, false otherwise.
	 */
	public boolean changeFamilyDoctor(Patient patient, FamilyDoctor newDoctor);

	// FAMILY DOCTOR
	/**
	 * @return a list with all the family doctors, or an empty list if the database
	 *         doesn't have any row.
	 */
	public Collection<FamilyDoctor> listAllFamilyDoctors();
	
	/**
	 * Update the family doctor data.
	 * 
	 * @param doctor the doctor to be updated.
	 * @param cap    the primary healthcare center of the family doctor.
	 * @return true if the family doctor was updated, false otherwise.
	 */
	public boolean updateFamilyDoctorData(FamilyDoctor doctor, PrimaryHealthCareCenter cap);

	/**
	 * Change the cap of a family doctor.
	 * 
	 * @param doctor the family doctor to change the cap.
	 * @param cap    the new cap of the family doctor.
	 * @return true if the primary healthcare center is changed, false otherwise.
	 */
	public boolean changePrimaryHealthcareCenter(FamilyDoctor doctor, PrimaryHealthCareCenter cap);

	// SPECIALIST DOCTOR
	/**
	 * @return a list with all the specialist doctors, or an empty list if the database
	 *         doesn't have any row.
	 */
	public Collection<SpecialistDoctor> listAllSpecialistDoctors();
	
	/**
	 * Update the specialist doctor data.
	 * 
	 * @param doctor     the doctor to be updated.
	 * @param speciality the new medical specialty of the doctor.
	 * @return true if the specialist doctor was updated, false otherwise.
	 */
	public boolean updateSpecialistDoctorData(SpecialistDoctor doctor, MedicalSpeciality speciality);

	// ADMINISTRATOR
	/**
	 * @return a list with all the administrators, or an empty list if the database
	 *         doesn't have any row.
	 */
	public Collection<Administrator> listAllAdministrators();
}
