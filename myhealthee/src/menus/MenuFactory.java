package menus;

import java.util.ArrayList;
import java.util.List;

import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import services.i18n.I18n;
import utils.SessionUtils;

/**
 * Menu generator for the dashboard.
 * 
 * @author adlo
 */
public class MenuFactory {

	// MENUS.
	private static List<Menu> MENUS = new ArrayList<Menu>();
	static {
		MENUS.add(new Menu(I18n.translate("menus.dashboard"), "fa-tachometer-alt", "homeView", Patient.class,
				FamilyDoctor.class, SpecialistDoctor.class, Administrator.class));
		MENUS.add(new Menu(I18n.translate("menus.users"), "fa-users", "usersView", Administrator.class));
		MENUS.add(new Menu(I18n.translate("menus.caps"), "fa-hospital", "capsView", Administrator.class));
		MENUS.add(new Menu(I18n.translate("menus.medicalSpecialities"), "fa-notes-medical", "medicalSpecialitiesView",
				Administrator.class));
		MENUS.add(new Menu(I18n.translate("menus.visits"), "fa-stethoscope", "visitsView", Administrator.class));
		MENUS.add(new Menu(I18n.translate("menus.medicalTests"), "fa-vial", "medicalTestView", Patient.class,
				FamilyDoctor.class, SpecialistDoctor.class, Administrator.class));
		MENUS.add(new Menu(I18n.translate("menus.questions"), "fa-comments", "questionsView", Patient.class,
				FamilyDoctor.class, Administrator.class));
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
