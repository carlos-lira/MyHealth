package menus;

import java.util.ArrayList;
import java.util.List;

import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import utils.SessionUtils;

public class VisitMenuFactory {

	// MENUS.
	private static List<Menu> MENUS = new ArrayList<Menu>();
	static {
		MENUS.add(new Menu("Añadir Visita", null, "addVisitView", Patient.class));
		MENUS.add(new Menu("Mis Visitas", null, "allPatientVisitsView", Patient.class));
		MENUS.add(new Menu("Mi Horario", null, "allScheduledVisitsView", FamilyDoctor.class));
		MENUS.add(new Menu("Administrar Visitas", null, "adminVisitsView", Administrator.class));
	}

	/**
	 * @return a list of menus.
	 */
	public List<Menu> getMenus() {
		User u = SessionUtils.getUser();
		List<Menu> menus = new ArrayList<Menu>();
		for (Menu m : VisitMenuFactory.MENUS) {
			if (m.getRoles().contains(u.getClass())) {
				menus.add(m);
			}
		}
		return menus;
	}
}
