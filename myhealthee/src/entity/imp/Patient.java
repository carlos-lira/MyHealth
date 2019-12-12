package entity.imp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import entity.User;

@Entity
@Table(name = "PATIENT", schema = "public")
public class Patient extends User {

	private static final long serialVersionUID = 5119359086731145181L;

	@Column(name = "NIF", nullable = false, length = 9, unique = true)
	private String nif;

	@Column(name = "NAME", nullable = false, length = 100)
	private String name;

	@Column(name = "SURNAMES", nullable = false, length = 100)
	private String surnames;

	// Getters & Setters
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}
}
