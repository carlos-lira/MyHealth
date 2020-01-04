package components.profile.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import components.profile.dao.ProfileFacadeRemote;
import components.systemadministration.dao.SystemAdministrationFacade;
import entity.User;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import utils.Messages;
import utils.SessionUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.logging.impl.Log4jLogger;

@Named("updatepatientprofile")
@SessionScoped
public class UpdatePatientProfileController implements Serializable {
	private static final long serialVersionUID = 6966891666475112223L;
    private static final Logger logger = Log4jLogger.getLogger(UpdateFamilyDoctorProfileController.class);

    @EJB
    private ProfileFacadeRemote ejb;

    private Patient patient;

    @PostConstruct
    public void init() {
        this.patient = (Patient) SessionUtils.getUser();

    }

    public void updatePatientData() {
        if (!isSamePassword(patient)) {
            Messages.addErrorGlobalMessage("The passwords need to be equals");
        } else {
            try {
                boolean actualizado = ejb.updatePatientData(
                        String.valueOf(patient.getId()),
                        patient.getNif(),
                        patient.getName(),
                        patient.getSurnames(),
                        patient.getPassword(),
                        patient.getEmail()
                );

                if (actualizado) {
                    Messages.addInfoGlobalMessage("Datos personales de paciente actualizados correctamente.");
                } else {
                    Messages.addErrorGlobalMessage("Error actualizando datos personales de paciente. Por favor inténtalo de nuevo.");
                }
            }
            catch(Exception e) {
                Messages.addErrorGlobalMessage("Error actualizando datos personales de paciente. Por favor inténtalo de nuevo.");
            }
        }
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    private boolean isSamePassword(User user) {
        String password = user.getPassword();
        String repeatPassword = user.getRepeatPassword();
        return password != null && password.equals(repeatPassword);
    }
}
