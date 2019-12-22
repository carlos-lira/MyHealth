package profile.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.imp.FamilyDoctor;
import entity.imp.MedicalSpeciality;
import entity.imp.PrimaryHealthCareCenter;

/**
 * Profile EJB.
 * 
 * @author apeleteiro
 */
@Stateless
public class ProfileFacade implements ProfileFacadeRemote {

	//private static final Logger logger = Log4jLogger.getLogger(ProfileFacade.class);
	//private static final HashAlgorithm algorithm = HashAlgorithm.MD5;
	
	@PersistenceContext(name = "myhealthee")
	private EntityManager em;

	@Override
	public boolean changeFamilyDoctor(String id, FamilyDoctor newDoctor) {
		return false;
	}

	@Override
	public boolean registerPatient(String id, String nif, String name, String surname, String password, String email) {
		return false;
	}

	@Override
	public boolean registerSpecialistDoctor(String id, String nif, String name, String surname, String password, String email, MedicalSpeciality speciality) {
		return false;
	}

	@Override
	public boolean registerFamilyDoctor(String id, String nif, String name, String surname, String password, String email, PrimaryHealthCareCenter cap) {
		return false;
	}

	@Override
	public boolean updatePatientData(String id, String nif, String name, String surname, String password, String email) {
		return false;
	}

	@Override
	public boolean updateSpecialistDoctorData(String id, String nif, String name, String surname, String password, String email, MedicalSpeciality speciality) {
		return false;
	}

	@Override
	public boolean updateFamilyDoctorData(String id, String nif, String name, String surname, String password, String email, PrimaryHealthCareCenter cap) {
		return false;
	}

	@Override
	public boolean changePrimaryHealthCareCenter(String id, PrimaryHealthCareCenter newCenter) {
		return false;
	}
}
