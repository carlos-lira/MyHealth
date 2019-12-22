package components.systemadministration.dao;

import java.util.Collection;

import javax.ejb.Remote;

import entity.User;
import entity.imp.FamilyDoctor;
import entity.imp.MedicalSpeciality;
import entity.imp.PrimaryHealthCareCenter;

/**
 * System administration EJB remote.
 * 
 * @author adlo
 */
@Remote
public interface SystemAdministrationFacadeRemote {

	// Sign in and Sign up operations
	/**
	 * Login to the system.
	 * 
	 * @param id       the id of the user.
	 * @param password the password of the user.
	 * @return the User if login successful, null otherwise.
	 */
	public User login(String id, String password);

	/**
	 * Logout from the system,
	 */
	public void logout();

	// User CRUD Operations
	/**
	 * @return a list with all the users, or an empty list if the database doesn't
	 *         have any row.
	 */
	public Collection<User> listAllUsers();

	/**
	 * Get user by id
	 * 
	 * @param id the id of the user, username or email.
	 * @return the user if found, null otherwise.
	 */
	public User getUser(String id);

	/**
	 * Add a user to the system.
	 * 
	 * @param user the User to be added.
	 * @return true if the user is add correctly, false otherwise.
	 */
	public boolean addUser(User user);

	/**
	 * Remove user from the system.
	 * 
	 * @param user the User to be removed.
	 * @return true if the user is removed correctly, false otherwise.
	 */
	public boolean removeUser(User user);

	/**
	 * Remove user by id.
	 * 
	 * @param id the id of the user, username, email.
	 * @return true if the user is removed correctly, false otherwise.
	 */
	public boolean removeUser(String id);

	// CAP CRUD operations
	/**
	 * @return a list with all the primary healthcare centers, or an empty list if
	 *         the database doesn't have any row.
	 */
	public Collection<PrimaryHealthCareCenter> listAllCAPs();

	/**
	 * Get a primary healthcare center by the name.
	 * 
	 * @param name the name of the center.
	 * @return the primary health care center identified by name, null otherwise.
	 */
	public PrimaryHealthCareCenter getCAP(String name);

	/**
	 * Add a primary healthcare center to the system.
	 * 
	 * @param name     the name of the center.
	 * @param location the location of the center.
	 */
	public void addCAP(String name, String location);

	/**
	 * Update the location of a primary healthcare center.
	 * 
	 * @param name     the name of the center.
	 * @param location the new location of the center.
	 */
	public void updateCAP(String name, String location);

	/**
	 * Delete a healthcare center.
	 * 
	 * @param name the name of the center.
	 */
	public void deleteCAP(String name);

	/**
	 * Get all family doctors in a primary healthcare center.
	 * 
	 * @param cap the primary healthcare center to get all the family doctors.
	 * @return a list with all the family doctors in a primary healthcare center.
	 */
	public Collection<FamilyDoctor> listAllFamilyDoctorsByCAP(PrimaryHealthCareCenter cap);

	// Medical Specialty CRUD operations
	/**
	 * @return a list with all the medical specialty, or an empty list if the
	 *         database doesn't have any row.
	 */
	public Collection<MedicalSpeciality> listAllMedicalSpecialities();

	/**
	 * Get a medical specialty.
	 * 
	 * @param name the name of the medical specialty
	 * @return the medical specialty identified by name, null otherwise.
	 */
	public MedicalSpeciality getMedicalSpecialty(String name);

	/**
	 * Add a medical specialty to the system.
	 * 
	 * @param name        the name of the medical specialty.
	 * @param description the description of the medical specialty.
	 */
	public void addMedicalSpecialty(String name, String description);

	/**
	 * Update a medical specialty.
	 * 
	 * @param name        the name of the medical specialty.
	 * @param description the new description of the medical specialty.
	 */
	public void updateMedicalSpecialty(String name, String description);

	/**
	 * Delete a medical specialty.
	 * 
	 * @param name the name of the medical specialty.
	 */
	public void deleteMedicalSpecialty(String name);
}
