package entity.imp;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import entity.User;
import utils.QueryNames;

/**
 * Administrator entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "ADMINISTRATOR", schema = "public")
@NamedQueries({ 
	@NamedQuery(name = QueryNames.GET_ALL_ADMINISTRATORS, query = "SELECT u FROM Administrator u"),
	@NamedQuery(name = QueryNames.GET_ADMINISTRATOR, query = "SELECT u FROM Administrator u WHERE u.email = :email OR u.username = :username")
})
public class Administrator extends User {
	private static final long serialVersionUID = -4581485990447846739L;
}
