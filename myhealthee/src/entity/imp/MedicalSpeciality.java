package entity.imp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
}
