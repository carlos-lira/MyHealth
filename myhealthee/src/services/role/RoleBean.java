package services.role;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import utils.SessionUtils;

/**
 * Bean to check the type of the user based on the instance of a user.
 * 
 * @author adlo
 */
@Named("role")
@SessionScoped
public class RoleBean implements Serializable {
	private static final long serialVersionUID = 4463203882887681742L;

	/* Fields */
	private User user;

	@PostConstruct
	public void init() {
		this.user = SessionUtils.getUser();
	}

	// ACTIONS
	/**
	 * @return true if a user is a instance of patient, false otherwise.
	 */
	public boolean isPatient(User user) {
		return user instanceof Patient;
	}

	/**
	 * @return true if the user in session is a instance of patient, false
	 *         otherwise.
	 */
	public boolean isPatient() {
		return this.isPatient(user);
	}

	/**
	 * @return true if a user is a instance of family doctor, false otherwise.
	 */
	public boolean isFamilyDoctor(User user) {
		return user instanceof FamilyDoctor;
	}
	
	/**
	 * @return true if the user in session is a instance of family doctor, false
	 *         otherwise.
	 */
	public boolean isFamilyDoctor() {
		return this.isFamilyDoctor(user);
	}

	/**
	 * @return true if a user is a instance of specialist doctor, false otherwise.
	 */
	public boolean isSpecialistDoctor(User user) {
		return user instanceof SpecialistDoctor;
	}
	
	/**
	 * @return true if the user in session is a instance of specialist doctor, false
	 *         otherwise.
	 */
	public boolean isSpecialistDoctor() {
		return this.isSpecialistDoctor(user);
	}

	/**
	 * @return true if a user is a doctor, false otherwise.
	 */
	public boolean isDoctor(User user) {
		return this.isFamilyDoctor(user) || this.isSpecialistDoctor(user);
	}
	
	/**
	 * @return true if the user in session is a doctor, false otherwise.
	 */
	public boolean isDoctor() {
		return this.isDoctor(user);
	}

	/**
	 * @return true if a user is a instance of administrator, false otherwise.
	 */
	public boolean isAdministrator(User user) {
		return user instanceof Administrator;
	}
	
	/**
	 * @return true if the user in session is a instance of administrator, false
	 *         otherwise.
	 */
	public boolean isAdministrator() {
		return this.isAdministrator(user);
	}
}
