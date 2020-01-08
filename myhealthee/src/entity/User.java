package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import utils.QueryNames;

/**
 * Users entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "USERS")
@NamedQueries({ 
	@NamedQuery(name = QueryNames.GET_ALL_USERS, query = "FROM User u"),
	@NamedQuery(name = QueryNames.GET_USER, query = "FROM User u WHERE u.email = :email OR u.username = :username") 
})
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {
	private static final long serialVersionUID = -766782089323139033L;

	@Column(name = "USERNAME", length = 200, nullable = true, unique = true)
	private String username;

	@Column(name = "EMAIL", length = 255, nullable = false, unique = true)
	private String email;

	@Column(name = "PASSWORD", length = 255, nullable = false)
	private String password;

	@Transient
	private String repeatPassword;

	// Methods
	/**
	 * Copy the fields of a user.
	 * 
	 * @param u the user to copy the fields.
	 */
	public void copy(User u) {
		this.setUsername(u.getUsername());
		this.setEmail(u.getEmail());
		this.setPassword(u.getPassword());
		this.setRepeatPassword(u.getRepeatPassword());
	}

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

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
}
