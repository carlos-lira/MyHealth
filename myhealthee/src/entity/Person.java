package entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * User with person fields.
 * 
 * @author adlo
 */
@MappedSuperclass
public class Person extends User {

	@Column(name = "NIF", nullable = false, length = 9, unique = true)
	private String nif;

	@Column(name = "NAME", nullable = false, length = 100)
	private String name;

	@Column(name = "SURNAMES", nullable = false, length = 100)
	private String surnames;

	// Methods
	/**
	 * Copy the fields of a person.
	 * 
	 * @param p the person to copy the fields.
	 */
	public void copy(Person p) {
		this.setNif(p.getNif());
		this.setName(p.getName());
		this.setSurnames(p.getSurnames());
		this.setUsername(p.getUsername());
		this.setEmail(p.getEmail());
		this.setPassword(p.getPassword());
		this.setRepeatPassword(p.getRepeatPassword());
	}
	
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
