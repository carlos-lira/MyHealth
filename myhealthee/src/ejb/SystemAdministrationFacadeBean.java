package ejb;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.AdministratorJPA;
import jpa.FamilydoctorJPA;
import jpa.MedicalspecialtyJPA;
import jpa.PrimaryhealthcarecenterJPA;

@Stateless
public class SystemAdministrationFacadeBean implements SystemAdministrationFacadeRemote {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="MyHealth") 
	private EntityManager entman;

	//fperezgon: Entiendo que id es en realidad email, no lo cambio para mantener interfaz 
	//           definida en el punto de partida, mapeo a email de forma interna
	@Override
	public boolean login(String id, String password) {
		boolean acceso = false;
		try
		{
			@SuppressWarnings("unchecked")
			Collection<AdministratorJPA> administrators = entman.createQuery("FROM AdministratorJPA b WHERE b.email = ?1 and b.password = ?2")
								.setParameter(1, id)
								.setParameter(2,password)
								.getResultList();
			if (!administrators.isEmpty() || administrators.size()==1)
			{
				acceso = true;	
				// TODO Registro en el sistema para permitir acceso a paginas
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		}
		
		return acceso;
	}

	@Override
	public void logout() {
		// TODO Eliminar usuario de registro en el sistema

	}

	@Override
	public void addCAP(String name, String location) {
		// Obtenemos el ultimo ID utilizado
		long lastUsedId = 0;
		@SuppressWarnings("unchecked")
		Collection<PrimaryhealthcarecenterJPA> CAPs = entman.createQuery("FROM PrimaryhealthcarecenterJPA b order by 1 desc").getResultList();
		if (!CAPs.isEmpty() || CAPs.size()==1)
		{
			Iterator<PrimaryhealthcarecenterJPA> iter =CAPs.iterator();
			lastUsedId = ((PrimaryhealthcarecenterJPA) iter.next()).getId();				
		}

		PrimaryhealthcarecenterJPA CAP = new PrimaryhealthcarecenterJPA(lastUsedId+1,name, location);
		entman.persist(CAP);
	
	}

	@Override
	public void deleteCAP(String name) {
		PrimaryhealthcarecenterJPA CAP = entman.find(PrimaryhealthcarecenterJPA.class, name);
		entman.remove(CAP);


	}

	@Override
	public Collection<?> listAllCAPs() {
		@SuppressWarnings("unchecked")
		Collection<PrimaryhealthcarecenterJPA> allCAPs = entman.createQuery("from PrimaryhealthcarecenterJPA order by 1").getResultList();
		return allCAPs;
	}

	@Override
	public void updateCAP(String name, String location) {
		PrimaryhealthcarecenterJPA CAP = entman.find(PrimaryhealthcarecenterJPA.class, name);
		CAP.setName(name);
		CAP.setLocation(location);
		
	}

	@Override
	public void registerAdministrator(String email, String password) {
		// Obtenemos el ultimo ID utilizado
		long lastUsedId = 0;
		@SuppressWarnings("unchecked")
		Collection<AdministratorJPA> administratorList = entman.createQuery("FROM AdministratorJPA b order by 1 desc").getResultList();
		if (!administratorList.isEmpty() || administratorList.size()==1)
		{
			Iterator<AdministratorJPA> iter =administratorList.iterator();
			lastUsedId = ((AdministratorJPA) iter.next()).getId();				
		}

		AdministratorJPA administrator = new AdministratorJPA(lastUsedId+1,email, password);
		entman.persist(administrator);

	}

	@Override
	public Collection<?> listAllFamilyDoctorsByCAP(PrimaryhealthcarecenterJPA cap) {
		@SuppressWarnings("unchecked")
		Collection<FamilydoctorJPA> allFamilydoctor = entman.createQuery("from FamilydoctorJPA where name like ? order by 1")
			.setParameter(1, cap.getName())
			.getResultList();
		return allFamilydoctor;
	}

	@Override
	public MedicalspecialtyJPA getMedicalSpecialty(String name) {
		MedicalspecialtyJPA medicalspecialty = null;
		try
		{
			@SuppressWarnings("unchecked")
			Collection<MedicalspecialtyJPA> medicalspecialities = entman.createQuery("FROM MedicalspecialtyJPA b WHERE b.id = ?").setParameter(1, name).getResultList();
			if (!medicalspecialities.isEmpty() || medicalspecialities.size()==1)
			{
				Iterator<MedicalspecialtyJPA> iter =medicalspecialities.iterator();
				medicalspecialty = (MedicalspecialtyJPA) iter.next();				
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return medicalspecialty;

	}

	@Override
	public void deleteMedicalSpecialty(String name) {
		MedicalspecialtyJPA medicalSpecialty = entman.find(MedicalspecialtyJPA.class, name);
		entman.remove(medicalSpecialty);

	}

	@Override
	public Collection<?> listAllMedicalSpecialities() {
		@SuppressWarnings("unchecked")
		Collection<MedicalspecialtyJPA> allMedicalspecialities = entman.createQuery("from MedicalspecialtyJPA order by 1").getResultList();
		return allMedicalspecialities;
	}

	@Override
	public void updateMedicalSpecialty(String name, String description) {
		MedicalspecialtyJPA medicalSpecialty = entman.find(MedicalspecialtyJPA.class, name);
		medicalSpecialty.setName(name);
		medicalSpecialty.setDescription(description);

	}

	@Override
	public void addMedicalSpecialty(String name, String description) {
		// Obtenemos el ultimo ID utilizado
		long lastUsedId = 0;
		@SuppressWarnings("unchecked")
		Collection<MedicalspecialtyJPA> medicalSpecialities = entman.createQuery("FROM MedicalspecialtyJPA b order by 1 desc").getResultList();
		if (!medicalSpecialities.isEmpty() || medicalSpecialities.size()==1)
		{
			Iterator<MedicalspecialtyJPA> iter =medicalSpecialities.iterator();
			lastUsedId = ((MedicalspecialtyJPA) iter.next()).getId();				
		}

		MedicalspecialtyJPA medicalSpecialty = new MedicalspecialtyJPA(lastUsedId+1,name, description);
		entman.persist(medicalSpecialty);
		

	}

	@Override
	public PrimaryhealthcarecenterJPA getCAP(String name) {
		PrimaryhealthcarecenterJPA CAP = null;
		try
		{
			@SuppressWarnings("unchecked")
			Collection<PrimaryhealthcarecenterJPA> CAPs = entman.createQuery("FROM PrimaryhealthcarecenterJPA b WHERE b.id = ?").setParameter(1, name).getResultList();
			if (!CAPs.isEmpty() || CAPs.size()==1)
			{
				Iterator<PrimaryhealthcarecenterJPA> iter =CAPs.iterator();
				CAP = (PrimaryhealthcarecenterJPA) iter.next();				
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return CAP;
	}

}
