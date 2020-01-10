package components.profile.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.profile.dao.ProfileFacadeRemote;
import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.imp.Administrator;
import services.crud.Operation;
import services.i18n.I18n;
import utils.Messages;
import utils.SessionUtils;
import utils.StringUtils;

/**
 * Administrator profile managed bean.
 * 
 * @author apeleteiro
 * @author adlo
 */
@Named("administratorProfile")
@SessionScoped
public class AdministratorProfileController implements Serializable {
	private static final long serialVersionUID = 9011282058318022422L;

	@EJB
	private ProfileFacadeRemote ejb;

	@EJB
	private SystemAdministrationFacadeRemote ejbSystemAdministration;

	/* Fields */
	private Operation mode;
	private Administrator administrator;
	private List<Administrator> listAdministrators;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.administrator = new Administrator();
		this.listAllAdministrators();
	}

	/**
	 * @return the profile view page.
	 */
	public String redirectProfile() {
		this.administrator = (Administrator) SessionUtils.getUser();
		return "profileView";
	}
	
	// MODAL ACTIONS
	public String openModal(Operation operation) {
		this.mode = operation;
		this.administrator = new Administrator();
		return null;
	}

	public String openModal(Operation operation, Administrator admin) {
		this.mode = operation;
		this.administrator = admin;
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	public String addAdministrator() {
		String password = this.administrator.getPassword();
		String repeatPassword = this.administrator.getRepeatPassword();
		if (!StringUtils.isSameString(password, repeatPassword)) {
			Messages.addWarnGlobalMessage(I18n.translate("gobal.error.100000"));
			this.clear();
			return null;
		}
		if (!ejbSystemAdministration.addUser(this.administrator)) {
			Messages.addErrorGlobalMessage(I18n.translate("profile.error.000001"));
		}
		this.clear();
		this.listAllAdministrators();
		return null;
	}

	public String updateAdministrator() {
		String password = this.administrator.getPassword();
		if (password != null && !password.equals("")) {
			String repeatPassword = this.administrator.getRepeatPassword();
			if (!StringUtils.isSameString(password, repeatPassword)) {
				Messages.addWarnGlobalMessage(I18n.translate("gobal.error.100000"));
				this.clear();
				return null;
			}
		}
		if (!ejbSystemAdministration.updateUser(this.administrator)) {
			Messages.addWarnGlobalMessage(I18n.translate("profile.error.000002"));
		}
		this.clear();
		this.listAllAdministrators();
		return null;
	}

	public String deleteAdministrator() {
		if (!ejbSystemAdministration.removeUser(this.administrator.getUsername())) {
			Messages.addWarnGlobalMessage(I18n.translate("profile.error.000003"));
		}
		this.clear();
		this.listAllAdministrators();
		return null;
	}
	
	public String updateProfile() {
		String password = this.administrator.getPassword();
		if (password != null && !password.equals("")) {
			String repeatPassword = this.administrator.getRepeatPassword();
			if (!StringUtils.isSameString(password, repeatPassword)) {
				Messages.addWarnGlobalMessage(I18n.translate("gobal.error.100000"));
				return null;
			}
		}
		if (!ejbSystemAdministration.updateUser(this.administrator)) {
			Messages.addWarnGlobalMessage(I18n.translate("profile.error.000002"));
		}
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.administrator = null;
	}

	private void listAllAdministrators() {
		this.listAdministrators = (List<Administrator>) ejb.listAllAdministrators();
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	public List<Administrator> getListAdministrators() {
		return listAdministrators;
	}
}
