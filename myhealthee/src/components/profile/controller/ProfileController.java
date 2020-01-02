package components.profile.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import menus.Menu;
import menus.ProfileMenuFactory;

@Named("profile")
@SessionScoped
public class ProfileController implements Serializable {
	private static final long serialVersionUID = 9211805378364561644L;

	private List<Menu> navPages;

	@PostConstruct
	public void init() {
		this.navPages = new ProfileMenuFactory().getMenus();
	}

	// Getters
	public List<Menu> getNavPages() {
		return navPages;
	}
}
