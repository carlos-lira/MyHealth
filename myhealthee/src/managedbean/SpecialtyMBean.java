package managedbean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.SystemAdministrationFacadeRemote;
import jpa.MedicalspecialtyJPA;

/**
 * Managed Bean SpecialtyMBean
 */
@Named("specialty")
@RequestScoped
public class SpecialtyMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private SystemAdministrationFacadeRemote specialitiesRemote;
	//stores the name and description of medical specialities
	private Collection<MedicalspecialtyJPA> specialitiesList;
	//stores MedicalspecialtyJPA instance. Initialized at instantiation
	protected MedicalspecialtyJPA dataSpecialty;
	//stores MedicalspecialtyJPA name
	protected String name = "";
	protected String description = "";
	protected String mode = "";
	protected String nameParam = "";
	
	public String getNameParam() {
		return nameParam;
	}
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	public void setNameParam(String nameParam) throws Exception {
		this.nameParam = nameParam;
		if (!this.nameParam.equals("")) {
			dataSpecialty = getSpecialty(nameParam);
			this.name = dataSpecialty.getName();
			this.description = dataSpecialty.getDescription();
		}
	}

	/* Method Will Avoid Multiple Calls To DB For Fetching The Records. If This Is Not Used & Data Is Fetched From Getter Method, JSF DataTable Will Make Multiple Calls To DB*/
	@PostConstruct
	public void init() throws Exception {
		specialitiesList = getSpecialtyList();
	}
	
	@SuppressWarnings("unchecked")
	private Collection<MedicalspecialtyJPA> getSpecialtyList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		//screen = 0;
		specialitiesRemote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		return  (Collection<MedicalspecialtyJPA>)specialitiesRemote.listAllMedicalSpecialities();
	}
	/* Method Used To Fetch All Records From The Database */
	public Collection<MedicalspecialtyJPA> specialitiesList() {
		return specialitiesList;
	}
	public MedicalspecialtyJPA getSpecialty(String name) throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		//screen = 0;
		specialitiesRemote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		return  (MedicalspecialtyJPA)specialitiesRemote.getMedicalSpecialty(name);
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void  addDataSpecialty() throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		specialitiesRemote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		specialitiesRemote.addMedicalSpecialty(name, description); 
	}
	public void deleteSpecialty(String name) throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		specialitiesRemote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		specialitiesRemote.deleteMedicalSpecialty(name);
		specialitiesList = getSpecialtyList();
		//return "listSpecialities_bootstrap?faces-redirect=true";
	}
	public void  updateDataSpecialty() throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		specialitiesRemote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		specialitiesRemote.updateMedicalSpecialty(name, description); 
	}
	public void  saveDataSpecialty(String mode) throws Exception
	{
		if (mode.equals("edit")) {
			updateDataSpecialty();
		} else {
			addDataSpecialty();
		}
	}

	/*public MedicalspecialtyJPA getDataSpecialty()
	{
		return dataSpecialty;
	}	
	public void setDataSpecialty() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		showSpecialtyRemote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		dataSpecialty = (MedicalspecialtyJPA) showSpecialtyRemote.getMedicalSpecialty(nameSpecialty);
	}
	public void  updateDataSpecialty() throws Exception
	{
		if (dataSpecialty.getDescription().equals("")) throw new Exception("Error al actualizar. Campo Descripcion no cumplimentado.");
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		showSpecialtyRemote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		showSpecialtyRemote.updateMedicalSpecialty(dataSpecialty.getName(), dataSpecialty.getDescription()); 
		dataSpecialty = (MedicalspecialtyJPA) showSpecialtyRemote.getMedicalSpecialty(nameSpecialty);
	}	
	public void  addDataSpecialty() throws Exception
	{
		this.nameSpecialty = dataSpecialty.getName();
		//if (dataSpecialty.getDescription().equals("")) throw new Exception("Error al actualizar. Campo Descripcion no cumplimentado.");
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		showSpecialtyRemote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		showSpecialtyRemote.addMedicalSpecialty(dataSpecialty.getName(), dataSpecialty.getDescription()); 
		dataSpecialty = (MedicalspecialtyJPA) showSpecialtyRemote.getMedicalSpecialty(nameSpecialty);
		this.mode = "";
	}*/	
}
