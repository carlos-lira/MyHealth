package menus;

import entity.User;
import entity.imp.Administrator;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import utils.SessionUtils;

import java.util.ArrayList;
import java.util.List;

public class ProfileMenuFactory {
    // MENUS.
    private static List<Menu> MENUS = new ArrayList<Menu>();
    static {
        MENUS.add(new Menu("Actualizar datos personales", null, "updatePatientProfileView", Patient.class));
        MENUS.add(new Menu("Actualizar datos personales", null, "updateFamilyDoctorProfileView", FamilyDoctor.class));
        MENUS.add(new Menu("Actualizar datos personales", null, "updateSpecialistDoctorProfileView", SpecialistDoctor.class));
        MENUS.add(new Menu("Seleccionar m�dico de familia", null, "selectFamilyDoctorView", Patient.class));
        MENUS.add(new Menu("Seleccionar centro de atenci�n primaria", null, "selectCapView", FamilyDoctor.class));
        MENUS.add(new Menu("Registrar paciente", null, "registerPatientView", Administrator.class));
        MENUS.add(new Menu("Registrar m�dico de familia", null, "registerFamilyDoctorView", Administrator.class));
        MENUS.add(new Menu("Registrar m�dico especialista", null, "registerSpecialistDoctorView", Administrator.class));
    }

    /**
     * @return a list of menus.
     */
    public List<Menu> getMenus() {
        User u = SessionUtils.getUser();
        List<Menu> menus = new ArrayList<Menu>();
        for (Menu m : ProfileMenuFactory.MENUS) {
            if (m.getRoles().contains(u.getClass())) {
                menus.add(m);
            }
        }
        return menus;
    }
}
