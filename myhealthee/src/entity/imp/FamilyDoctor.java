package entity.imp;

import javax.persistence.Entity;
import javax.persistence.Table;

import entity.Doctor;

/**
 * Family doctor entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "FAMILY_DOCTOR")
public class FamilyDoctor extends Doctor {
	private static final long serialVersionUID = 6910880586339922197L;
}
