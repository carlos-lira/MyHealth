package entity.imp;

import javax.persistence.Entity;
import javax.persistence.Table;

import entity.User;

/**
 * Administrator entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "ADMINISTRATOR")
public class Administrator extends User {
	private static final long serialVersionUID = -4581485990447846739L;
}
