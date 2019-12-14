package menus;

import java.util.ArrayList;
import java.util.List;

import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import utils.SessionUtils;

public class MenuFactory {

	// MENUS.
	private static List<Menu> MENUS = new ArrayList<Menu>();
	static {
		MENUS.add(new Menu("Dashboard", "fa-tachometer-alt", "homeView", Patient.class, FamilyDoctor.class,
				SpecialistDoctor.class, Administrator.class));
		MENUS.add(new Menu("Users", "fa-users", "usersView", Administrator.class));
		MENUS.add(new Menu("Caps", "fa-hospital", "capsView", Administrator.class));
		MENUS.add(new Menu("Medical specialities", "fa-notes-medical", "medicalSpecialitiesView", Administrator.class));
	}

	/**
	 * @return a list of menus.
	 */
	public List<Menu> getMenus() {
		User u = SessionUtils.getUser();
		List<Menu> menus = new ArrayList<Menu>();
		for (Menu m : MenuFactory.MENUS) {
			if (m.getRoles().contains(u.getClass())) {
				menus.add(m);
			}
		}
		return menus;
	}
}
