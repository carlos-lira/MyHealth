package entity.imp;

import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import entity.BaseEntity;
import utils.QueryNames;

/**
 * Primary healthcare center entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "PRIMARY_HEALTHCARE_CENTER")
@NamedQueries({
	@NamedQuery(name = QueryNames.GET_ALL_PRIMARY_HELTHCARE_CENTERS, query = "FROM PrimaryHealthCareCenter phc"),
	@NamedQuery(name = QueryNames.GET_PRIMARY_HEALTHCARE_CENTER_BY_NAME, query = "FROM PrimaryHealthCareCenter phc WHERE phc.name = :name") 
})
public class PrimaryHealthCareCenter extends BaseEntity {
	private static final long serialVersionUID = 6573584678308375182L;

	@Column(name = "NAME", nullable = false, length = 100, unique = true)
	private String name;

	@Column(name = "LOCATION", nullable = false, length = 500)
	private String location;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	@JoinColumn(name = "FAMILY_DOCTOR_ID")
	private List<FamilyDoctor> familyDoctors;

	// Sync methods
	/**
	 * Add family doctor
	 * 
	 * @param familyDoctor the family doctor
	 */
	public void addFamilyDoctor(FamilyDoctor familyDoctor) {
		this.familyDoctors.add(familyDoctor);
		familyDoctor.setPrimaryHealthcareCenter(this);
	}
	
	/**
	 * Remove family doctor
	 * 
	 * @param familyDoctor the family doctor.
	 */
	public void removeFamilyDoctor(FamilyDoctor familyDoctor) {
		familyDoctor.setPrimaryHealthcareCenter(null);
		this.familyDoctors.remove(familyDoctor);
	}
	
	/**
	 * Remove all family doctors.
	 */
	public void removeAllFamilyDoctors() {
		Iterator<FamilyDoctor> it = this.familyDoctors.iterator();
		while (it.hasNext()) {
			FamilyDoctor familyDoctor = it.next();
			familyDoctor.setPrimaryHealthcareCenter(null);
			it.remove();
		}
	}
	
	// Getters & Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<FamilyDoctor> getFamilyDoctors() {
		return familyDoctors;
	}

	public void setFamilyDoctors(List<FamilyDoctor> familyDoctors) {
		this.familyDoctors = familyDoctors;
	}
}
