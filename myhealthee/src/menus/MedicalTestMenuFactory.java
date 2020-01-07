package menus;

import entity.User;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import utils.SessionUtils;

import java.util.ArrayList;
import java.util.List;

public class MedicalTestMenuFactory {
    // MENUS.
    private static List<Menu> MENUS = new ArrayList<Menu>();
    static {
        MENUS.add(new Menu("Añadir una prueba médica", null, "addMedicalTestView", SpecialistDoctor.class));
        MENUS.add(new Menu("Consultar una prueba médica", null, "getMedicalTestView", FamilyDoctor.class));
        MENUS.add(new Menu("Hacer una pregunta al médico de familia", null, "askQuestionView", Patient.class));
        MENUS.add(new Menu("Responder a una pregunta de un paciente", null, "answerQuestionView", FamilyDoctor.class));
        MENUS.add(new Menu("Buscar un especialista por especialidad médica", null, "searchSpecialistDoctorView", Patient.class));
        MENUS.add(new Menu("Gestionar la imagen de una prueba médica", null, "manageImageView", SpecialistDoctor.class));
    }

    /**
     * @return a list of menus.
     */
    public List<Menu> getMenus() {
        User u = SessionUtils.getUser();
        List<Menu> menus = new ArrayList<Menu>();
        for (Menu m : MedicalTestMenuFactory.MENUS) {
            if (m.getRoles().contains(u.getClass())) {
                menus.add(m);
            }
        }
        return menus;
    }
}
