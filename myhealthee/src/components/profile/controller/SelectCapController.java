package components.profile.controller;

import components.profile.dao.ProfileFacadeRemote;
import entity.imp.FamilyDoctor;
import entity.imp.Patient;
import entity.imp.PrimaryHealthCareCenter;
import utils.Messages;
import utils.SessionUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("selectcap")
@SessionScoped
public class SelectCapController  implements Serializable {
	private static final long serialVersionUID = -3853074987188276382L;

	@EJB
	private ProfileFacadeRemote ejb;

	private String cap;

	private String newCap;

	@PostConstruct
	public void init() {
		FamilyDoctor familyDoctor = (FamilyDoctor) SessionUtils.getUser();
		PrimaryHealthCareCenter cap = familyDoctor.getPrimaryHealthcareCenter();
		if (cap != null) {
			this.cap = cap.getName().concat(" (").concat(cap.getLocation()).concat(")");
		} else {
			this.cap = "Ninguno asignado";
		}
	}

	public List<PrimaryHealthCareCenter> listAllCaps()
	{
		try {
			return ejb.listAllCaps();
		}
		catch(Exception e){
			return null;
		}
	}

	public String updateCap() {
		try {
			FamilyDoctor doctor = (FamilyDoctor) SessionUtils.getUser();
			PrimaryHealthCareCenter cap = doctor.getPrimaryHealthcareCenter();
			PrimaryHealthCareCenter newCap = (PrimaryHealthCareCenter) ejb.getCap(this.newCap);

			if (cap != null && cap.getName().equals(newCap.getName())) {
				Messages.addErrorGlobalMessage("El actual y el nuevo centro de atención primaria son el mismo. Seleccione uno distinto.");
				return null;
			} else {
				ejb.changePrimaryHealthCareCenter(doctor.getEmail(), newCap);
				return "selectCapView";
			}
		}
		catch(Exception e){
			Messages.addErrorGlobalMessage("Error seleccionando un centro de atención primaria. Por favor inténtalo de nuevo.");
			return null;
		}
	}

	public String getCap() {
		return cap;
	}

	public String getNewCap() {
		return newCap;
	}

	public void setNewCap(String newCap) {
		this.newCap = newCap;
	}
}
