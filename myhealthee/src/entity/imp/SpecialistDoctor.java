package entity.imp;

import javax.persistence.Entity;
import javax.persistence.Table;

import entity.Doctor;

@Entity
@Table(name = "SPECIALIST_DOCTOR")
public class SpecialistDoctor extends Doctor {
	private static final long serialVersionUID = -7014944904910704221L;
}
