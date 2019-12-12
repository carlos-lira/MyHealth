package entity.imp;

import javax.persistence.Entity;
import javax.persistence.Table;

import entity.Person;

/**
 * Patient entity.
 * 
 * @author adlo
 */
@Entity
@Table(name = "PATIENT")
public class Patient extends Person {
	private static final long serialVersionUID = 5119359086731145181L;
}
