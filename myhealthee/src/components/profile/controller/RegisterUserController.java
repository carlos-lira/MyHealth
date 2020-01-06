package components.profile.controller;

import components.profile.dao.ProfileFacadeRemote;
import entity.User;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import utils.Messages;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("registeruser")
@SessionScoped
public class RegisterUserController  implements Serializable {
	private static final long serialVersionUID = -7130994363141373697L;

    @EJB
    private ProfileFacadeRemote ejb;

    private Patient patient;
    private FamilyDoctor familyDoctor;
    private SpecialistDoctor specialistDoctor;

    @PostConstruct
    public void init() {
        this.patient = new Patient();
        this.familyDoctor = new FamilyDoctor();
        this.specialistDoctor = new SpecialistDoctor();
    }

    public void registerPatient() {
        if (!isSamePassword(patient)) {
            Messages.addErrorGlobalMessage("The passwords need to be equals");
        } else {
            try {
                boolean actualizado = ejb.registerPatient(
                        String.valueOf(patient.getId()),
                        patient.getNif(),
                        patient.getName(),
                        patient.getSurnames(),
                        patient.getPassword(),
                        patient.getUsername(),
                        patient.getEmail()
                );

                if (actualizado) {
                    Messages.addInfoGlobalMessage("Paciente registrado correctamente.");
                } else {
                    Messages.addErrorGlobalMessage("Error registrando paciente. Por favor inténtalo de nuevo.");
                }
            }
            catch(Exception e) {
                Messages.addErrorGlobalMessage("Error registrando paciente. Por favor inténtalo de nuevo.");
            }
        }
    }

    public void registerFamilyDoctor() {
        if (!isSamePassword(familyDoctor)) {
            Messages.addErrorGlobalMessage("The passwords need to be equals");
        } else {
            try {
                boolean actualizado = ejb.registerFamilyDoctor(
                        String.valueOf(familyDoctor.getId()),
                        familyDoctor.getNif(),
                        familyDoctor.getName(),
                        familyDoctor.getSurnames(),
                        familyDoctor.getPassword(),
                        familyDoctor.getUsername(),
                        familyDoctor.getEmail(),
                        familyDoctor.getPrimaryHealthcareCenter()
                );

                if (actualizado) {
                    Messages.addInfoGlobalMessage("Médico de familia registrado correctamente.");
                } else {
                    Messages.addErrorGlobalMessage("Error registrando médico de familia. Por favor inténtalo de nuevo.");
                }
            }
            catch(Exception e) {
                Messages.addErrorGlobalMessage("Error registrando médico de familia. Por favor inténtalo de nuevo.");
            }
        }
    }

    public void registerSpecialistDoctor() {
        if (!isSamePassword(specialistDoctor)) {
            Messages.addErrorGlobalMessage("The passwords need to be equals");
        } else {
            try {
                boolean actualizado = ejb.registerSpecialistDoctor(
                        String.valueOf(specialistDoctor.getId()),
                        specialistDoctor.getNif(),
                        specialistDoctor.getName(),
                        specialistDoctor.getSurnames(),
                        specialistDoctor.getPassword(),
                        specialistDoctor.getUsername(),
                        specialistDoctor.getEmail(),
                        specialistDoctor.getMedicalSpeciality()
                );

                if (actualizado) {
                    Messages.addInfoGlobalMessage("Médico de familia registrado correctamente.");
                } else {
                    Messages.addErrorGlobalMessage("Error registrando médico de familia. Por favor inténtalo de nuevo.");
                }
            }
            catch(Exception e) {
                Messages.addErrorGlobalMessage("Error registrando médico de familia. Por favor inténtalo de nuevo.");
            }
        }
    }

    private boolean isSamePassword(User user) {
        String password = user.getPassword();
        String repeatPassword = user.getRepeatPassword();
        return password != null && password.equals(repeatPassword);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public FamilyDoctor getFamilyDoctor() {
        return familyDoctor;
    }

    public void setFamilyDoctor(FamilyDoctor familyDoctor) {
        this.familyDoctor = familyDoctor;
    }

    public SpecialistDoctor getSpecialistDoctor() {
        return specialistDoctor;
    }

    public void setSpecialistDoctor(SpecialistDoctor specialistDoctor) {
        this.specialistDoctor = specialistDoctor;
    }
}
