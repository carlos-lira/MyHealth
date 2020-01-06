package components.profile.controller;

import components.profile.dao.ProfileFacadeRemote;
import entity.User;
import entity.imp.FamilyDoctor;
import utils.Messages;
import utils.SessionUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("updatefamilydoctorprofile")
@SessionScoped
public class UpdateFamilyDoctorProfileController implements Serializable {
	private static final long serialVersionUID = -5628159165028747010L;

    @EJB
    private ProfileFacadeRemote ejb;

    private FamilyDoctor familyDoctor;

    @PostConstruct
    public void init() {
        this.familyDoctor = (FamilyDoctor) SessionUtils.getUser();

    }

    public void updateFamilyDoctorData() {
        if (!isSamePassword(familyDoctor)) {
            Messages.addErrorGlobalMessage("The passwords need to be equals");
        } else {
            try {
                boolean actualizado = ejb.updateFamilyDoctorData(
                        String.valueOf(familyDoctor.getId()),
                        familyDoctor.getNif(),
                        familyDoctor.getName(),
                        familyDoctor.getSurnames(),
                        familyDoctor.getPassword(),
                        familyDoctor.getEmail(),
                        familyDoctor.getPrimaryHealthcareCenter()
                );

                if (actualizado) {
                    Messages.addInfoGlobalMessage("Datos personales de médico de familia actualizados correctamente.");
                } else {
                    Messages.addErrorGlobalMessage("Error actualizando datos personales de médico de familia. Por favor inténtalo de nuevo.");
                }
            }
            catch(Exception e) {
                Messages.addErrorGlobalMessage("Error actualizando datos personales de médico de familia. Por favor inténtalo de nuevo.");
            }
        }
    }

    public FamilyDoctor getFamilyDoctor() {
        return familyDoctor;
    }

    public void setFamilyDoctor(FamilyDoctor familyDoctor) {
        this.familyDoctor = familyDoctor;
    }

    private boolean isSamePassword(User user) {
        String password = user.getPassword();
        String repeatPassword = user.getRepeatPassword();
        return password != null && password.equals(repeatPassword);
    }
}
