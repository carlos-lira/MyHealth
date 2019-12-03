package ejb;

import java.util.Collection;

import javax.ejb.Remote;

import jpa.MedicalspecialtyJPA;
import jpa.PrimaryhealthcarecenterJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface SystemAdministrationFacadeRemote {

	//fperezgon: Entiendo que id es en realidad email, no lo cambio para mantener interfaz 
	//           definida en el punto de partida, mapeo a email de forma interna
	public boolean login(String id, String password); 
	public void logout();
	public void addCAP(String name, String location);
	public void deleteCAP(String name);
	public Collection<?> listAllCAPs();
	public void updateCAP(String name, String location);
	public void registerAdministrator(String email, String password);
	public Collection<?> listAllFamilyDoctorsByCAP(PrimaryhealthcarecenterJPA cap);
	public MedicalspecialtyJPA getMedicalSpecialty(String name);
	public void deleteMedicalSpecialty(String name);
	public Collection<?> listAllMedicalSpecialities();
	public void updateMedicalSpecialty(String name, String description);
	public void addMedicalSpecialty(String name, String description);
	public PrimaryhealthcarecenterJPA getCAP(String name);
}
