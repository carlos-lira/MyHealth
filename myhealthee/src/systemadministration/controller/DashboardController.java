package systemadministration.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import menus.Menu;
import menus.MenuFactory;
import systemadministration.dao.SystemAdministrationFacadeRemote;

@Named("dashboard")
@SessionScoped
public class DashboardController implements Serializable {
	private static final long serialVersionUID = 3414463670400022600L;

	@EJB
	private SystemAdministrationFacadeRemote ejb;
	
	/* Fields */
	private List<Menu> navPages = new MenuFactory().getMenus();
	
	// Getters & Setters
	public List<Menu> getNavPages() {
		return this.navPages;
	}
}
