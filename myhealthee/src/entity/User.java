package entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Abstract user entity.
 * 
 * @author adlo
 */
@MappedSuperclass
public abstract class User extends BaseEntity {

	@Column(name = "USERNAME", length = 200, nullable = true, unique = true)
	private String username;

	@Column(name = "EMAIL", length = 255, nullable = false, unique = true)
	private String email;

	@Column(name = "PASSWORD", length = 255, nullable = false)
	private String password;

	// Getters & Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
