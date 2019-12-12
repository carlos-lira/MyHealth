package entity.imp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import entity.BaseEntity;
import utils.QueryNames;

@Entity
@Table(name = "PRIMARY_HEALTHCARE_CENTER", schema = "public")
@NamedQueries({
	@NamedQuery(name = QueryNames.GET_ALL_PRIMARY_HELTHCARE_CENTERS, query = "SELECT phc FROM PrimaryHealthCareCenter phc"),
	@NamedQuery(name = QueryNames.GET_PRIMARY_HEALTHCARE_CENTER_BY_NAME, query = "SELECT phc FROM PrimaryHealthCareCenter phc WHERE phc.name = :name") 
})
public class PrimaryHealthCareCenter extends BaseEntity {

	private static final long serialVersionUID = 6573584678308375182L;

	@Column(name = "NAME", nullable = false, length = 100, unique = true)
	private String name;

	@Column(name = "LOCATION", nullable = false, length = 500)
	private String location;

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
}
