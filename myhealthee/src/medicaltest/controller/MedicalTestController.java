package medicaltest.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import menus.MedicalTestMenuFactory;
import menus.Menu;

@Named("medicaltest")
@SessionScoped
public class MedicalTestController implements Serializable {

	private static final long serialVersionUID = -4679047255683679241L;
	private List<Menu> navPages;

	@PostConstruct
	public void init() {
		this.navPages = new MedicalTestMenuFactory().getMenus();
	}

	// Getters
	public List<Menu> getNavPages() {
		return navPages;
	}
}
