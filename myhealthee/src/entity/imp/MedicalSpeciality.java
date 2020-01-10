package entity.imp;

import java.util.ArrayList;
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
 * Medical specialist entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "MEDICAL_SPECIALITY")
@NamedQueries({ 
	@NamedQuery(name = QueryNames.GET_ALL_MEDICAL_SPECIALITIES, query = "FROM MedicalSpeciality ms"),
	@NamedQuery(name = QueryNames.GET_MEDICAL_SPECIALITY_BY_NAME, query = "FROM MedicalSpeciality ms WHERE ms.name = :name") 
})
public class MedicalSpeciality extends BaseEntity {
	private static final long serialVersionUID = 7224485420069005482L;

	@Column(name = "NAME", nullable = false, length = 100, unique = true)
	private String name;

	@Column(name = "DESCRIPTION", nullable = false, length = 500)
	private String description;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = false)
	@JoinColumn(name = "MEDICAL_SPECIALITY_ID")
	private List<SpecialistDoctor> specialistDoctors = new ArrayList<SpecialistDoctor>();

	// Sync methods
	/**
	 * Add specialist doctor.
	 * 
	 * @param specialistDoctor
	 */
	public void addSpecialistDoctor(SpecialistDoctor specialistDoctor) {
		this.specialistDoctors.add(specialistDoctor);
		specialistDoctor.setMedicalSpeciality(this);
	}
	
	/**
	 * Remove specialist doctor
	 * 
	 * @param specialistDoctor
	 */
	public void removeSpecialistDoctor(SpecialistDoctor specialistDoctor) {
		specialistDoctor.setMedicalSpeciality(null);
		this.specialistDoctors.remove(specialistDoctor);
	}
	
	/**
	 * Remove all specialist doctors.
	 */
	public void removeAllSpecialistDoctors() {
		Iterator<SpecialistDoctor> it = this.specialistDoctors.iterator();
		while (it.hasNext()) {
			SpecialistDoctor specialistDoctor = it.next();
			specialistDoctor.setMedicalSpeciality(null);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SpecialistDoctor> getSpecialistDoctors() {
		return specialistDoctors;
	}

	public void setSpecialistDoctors(List<SpecialistDoctor> specialistDoctors) {
		this.specialistDoctors = specialistDoctors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	// Equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalSpeciality other = (MedicalSpeciality) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
