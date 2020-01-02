package components.profile.controller;

import components.profile.dao.ProfileFacadeRemote;
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

@Named("selectfamilydoctor")
@SessionScoped
public class SelectFamilyDoctorController implements Serializable {
	private static final long serialVersionUID = -7823366678781902314L;

    @EJB
    private ProfileFacadeRemote ejb;

	private String familyDoctor;

    private String newFamilyDoctor;

    @PostConstruct
    public void init() {
        Patient patient = (Patient) SessionUtils.getUser();
        FamilyDoctor doctor = patient.getFamilyDoctor();
        if (doctor != null) {
            this.familyDoctor = doctor.getName().concat(" ").concat(doctor.getSurnames());
        } else {
            this.familyDoctor = "Ninguno asignado";
        }
    }

    public List<FamilyDoctor> listAllFamilyDoctors()
    {
        try {
            return ejb.listAllFamilyDoctors();
        }
        catch(Exception e){
            return null;
        }
    }

    public String updateFamilyDoctor() {
        try {
            Patient patient = (Patient) SessionUtils.getUser();
            FamilyDoctor doctor = patient.getFamilyDoctor();
            FamilyDoctor newDoctor = (FamilyDoctor) ejb.getUser(this.newFamilyDoctor);

            if (doctor != null && doctor.getEmail().equals(newDoctor.getEmail())) {
                Messages.addErrorGlobalMessage("El actual y el nuevo m�dico de familia son el mismo. Seleccione uno distinto.");
                return null;
            } else {
                ejb.changeFamilyDoctor(patient.getEmail(), newDoctor);
                return "selectFamilyDoctorView";
            }
        }
        catch(Exception e){
            Messages.addErrorGlobalMessage("Error seleccionando un m�dico de familia. Por favor int�ntalo de nuevo.");
            return null;
        }
    }

    public String getFamilyDoctor() {
        return familyDoctor;
    }

    public String getNewFamilyDoctor() {
        return newFamilyDoctor;
    }

    public void setNewFamilyDoctor(String newFamilyDoctor) {
        this.newFamilyDoctor = newFamilyDoctor;
    }
}
