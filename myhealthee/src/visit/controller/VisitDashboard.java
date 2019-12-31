package visit.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import menus.Menu;
import menus.VisitMenuFactory;


@Named("visitdashboard")
@SessionScoped
public class VisitDashboard implements Serializable {

	private static final long serialVersionUID = 7142264007283605670L;
	
	private List<Menu> navPages;
	
	@PostConstruct
	public void init() {
		this.navPages = new VisitMenuFactory().getMenus();
	}

	// Getters
	public List<Menu> getNavPages() {
		return navPages;
	}
}