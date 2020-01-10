package entity.imp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import entity.BaseEntity;

/**
 * Response entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "RESPONSE")
public class Response extends BaseEntity {
	private static final long serialVersionUID = -8887002241079224951L;
	
	@Column(name = "MESSAGE", nullable = false, length = 2000)
	private String message;

	// Getters & Setters
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
