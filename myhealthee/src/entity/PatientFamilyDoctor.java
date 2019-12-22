package entity;

//import utils.QueryNames;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Patient family doctor entity.
 * 
 * @author adlo
 */
@Embeddable
@Table(name = "PATIENT_FAMILY_DOCTOR")
//@NamedQueries({
////	@NamedQuery(name = QueryNames.GET_ALL_FAMILY_DOCTOR_PATIENTS, query = "SELECT u FROM User u"),
////	@NamedQuery(name = QueryNames.GET_PATIENT_FAMILY_DOCTOR, query = "SELECT pfd FROM PatientFamilyDoctor pfd WHERE pfd.patient_id = :patient_id")
////})
@Inheritance(strategy = InheritanceType.JOINED)
public class PatientFamilyDoctor implements Serializable {
	private static final long serialVersionUID = 8251512097886174293L;

	@Column(name = "PATIENT_ID", nullable = false, unique = true)
	private String patient_id;

	@Column(name = "FAMILY_DOCTOR_ID", nullable = false, unique = false)
	private String family_doctor_id;

	// Getters & Setters
	public String getPatientId() {
		return patient_id;
	}

	public void setPatientId(String patient_id) {
		this.patient_id = patient_id;
	}

	public String getFamilyDoctorId() {
		return family_doctor_id;
	}

	public void setFamilyDoctorId(String family_doctor_id) {
		this.family_doctor_id = family_doctor_id;
	}

}
