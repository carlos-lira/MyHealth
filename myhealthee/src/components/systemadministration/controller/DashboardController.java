package components.systemadministration.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import components.systemadministration.dao.SystemAdministrationFacadeRemote;
import entity.User;
import menus.Menu;
import menus.MenuFactory;
import utils.StringUtils;

/**
 * Dashboard managed bean .
 * 
 * @author adlo
 */
@Named("dashboard")
@RequestScoped
public class DashboardController implements Serializable {
	private static final long serialVersionUID = 3414463670400022600L;

	@EJB
	private SystemAdministrationFacadeRemote ejb;

	/* Fields */
	private List<Menu> navPages;
	private Map<String, Integer> numberOfUsers;
	private List<String> numberOfUsersKeys = new ArrayList<String>();

	@PostConstruct
	public void init() {
		this.navPages = new MenuFactory().getMenus();
		this.numberOfUsers = new HashMap<String, Integer>();
		this.getListOfUsers();
	}

	// PRIVATE METHODS
	private void getListOfUsers() {
		List<User> users = (List<User>) ejb.listAllUsers();
		for (Iterator<User> it = users.iterator(); it.hasNext();) {
			User u = it.next();
			String key = StringUtils.splitCamelCase(u.getClass().getSimpleName());
			if (numberOfUsers.get(key) == null) {
				numberOfUsersKeys.add(key);
				numberOfUsers.put(key, 0);
			}
			Integer tmp = numberOfUsers.get(key);
			numberOfUsers.put(key, ++tmp);
		}
	}

	// Getters
	public List<Menu> getNavPages() {
		return navPages;
	}

	public Map<String, Integer> getNumberOfUsers() {
		return numberOfUsers;
	}

	public List<String> getNumberOfUsersKeys() {
		return numberOfUsersKeys;
	}
}
