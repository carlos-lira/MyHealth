package entity.imp;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import entity.Doctor;
import utils.QueryNames;

/**
 * Family doctor entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "FAMILY_DOCTOR")
@NamedQueries({
	@NamedQuery(name = QueryNames.GET_ALL_FAMILY_DOCTORS, query = "FROM FamilyDoctor u"),
	@NamedQuery(name = QueryNames.GET_ALL_FAMILY_DOCTORS_BY_CAP, query = "SELECT DISTINCT(d) FROM FamilyDoctor d JOIN FETCH d.patients WHERE d.primaryHealthcareCenter =:cap") 
})
public class FamilyDoctor extends Doctor {
	private static final long serialVersionUID = 6910880586339922197L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRIMARY_HEALTHCARE_CENTER_ID")
	private PrimaryHealthCareCenter primaryHealthcareCenter;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "FAMILY_DOCTOR_ID")
	private List<Visit> visits;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "FAMILY_DOCTOR_ID")
	private List<Patient> patients;

	// Getters & Setters
	public PrimaryHealthCareCenter getPrimaryHealthcareCenter() {
		return primaryHealthcareCenter;
	}

	public void setPrimaryHealthcareCenter(PrimaryHealthCareCenter primaryHealthcareCenter) {
		this.primaryHealthcareCenter = primaryHealthcareCenter;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
}
