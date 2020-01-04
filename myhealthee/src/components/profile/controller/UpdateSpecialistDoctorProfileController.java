package components.profile.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import components.profile.dao.ProfileFacadeRemote;
import components.systemadministration.dao.SystemAdministrationFacade;
import entity.User;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
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

@Named("updatespecialistdoctorprofile")
@SessionScoped
public class UpdateSpecialistDoctorProfileController implements Serializable {
	private static final long serialVersionUID = 5948297105297169616L;
	private static final Logger logger = Log4jLogger.getLogger(UpdateFamilyDoctorProfileController.class);

	@EJB
	private ProfileFacadeRemote ejb;

	private SpecialistDoctor specialistDoctor;

	@PostConstruct
	public void init() {
		this.specialistDoctor = (SpecialistDoctor) SessionUtils.getUser();

	}

	public void updateSpecialistDoctorData() {
		if (!isSamePassword(specialistDoctor)) {
			Messages.addErrorGlobalMessage("The passwords need to be equals");
		} else {
			try {
				boolean actualizado = ejb.updateSpecialistDoctorData(
						String.valueOf(specialistDoctor.getId()),
						specialistDoctor.getNif(),
						specialistDoctor.getName(),
						specialistDoctor.getSurnames(),
						specialistDoctor.getPassword(),
						specialistDoctor.getEmail(),
						specialistDoctor.getMedicalSpeciality()
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

	public SpecialistDoctor getSpecialistDoctor() {
		return specialistDoctor;
	}

	public void setSpecialistDoctor(SpecialistDoctor specialistDoctor) {
		this.specialistDoctor = specialistDoctor;
	}

	private boolean isSamePassword(User user) {
		String password = user.getPassword();
		String repeatPassword = user.getRepeatPassword();
		return password != null && password.equals(repeatPassword);
	}
}
