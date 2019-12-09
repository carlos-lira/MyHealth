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

import ejb.SystemAdministrationFacadeRemote;
import jpa.PrimaryhealthcarecenterJPA;

/**
 * Managed Bean CapMBean
 */
@Named("cap")
@RequestScoped
public class CapMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private SystemAdministrationFacadeRemote remote;
	//stores the name and location of CAPs
	private Collection<PrimaryhealthcarecenterJPA> capList;
	//stores PrimaryhealthcarecenterJPA instance. Initialized at instantiation
	protected PrimaryhealthcarecenterJPA dataCap;
	//stores PrimaryhealthcarecenterJPA name
	protected String name = "";
	protected String location = "";
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
			dataCap = getCap(nameParam);
			this.name = dataCap.getName();
			this.location = dataCap.getLocation();
		}
	}

	/* Method Will Avoid Multiple Calls To DB For Fetching The Records. If This Is Not Used & Data Is Fetched From Getter Method, JSF DataTable Will Make Multiple Calls To DB*/
	@PostConstruct
	public void init() throws Exception {
		capList = getCapList();
	}
	
	@SuppressWarnings("unchecked")
	private Collection<PrimaryhealthcarecenterJPA> getCapList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		//screen = 0;
		remote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		return  (Collection<PrimaryhealthcarecenterJPA>)remote.listAllCAPs();
	}
	/* Method Used To Fetch All Records From The Database */
	public Collection<PrimaryhealthcarecenterJPA> capList() {
		return capList;
	}
	public PrimaryhealthcarecenterJPA getCap(String name) throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		//screen = 0;
		remote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		return  (PrimaryhealthcarecenterJPA)remote.getCAP(name);
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void  addDataCap() throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		remote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		remote.addCAP(name, location); 
	}
	public void deleteCap(String name) throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		remote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		remote.deleteCAP(name);
		capList = getCapList();
		//return "listSpecialities_bootstrap?faces-redirect=true";
	}
	public void  updateDataCap() throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		remote = (SystemAdministrationFacadeRemote) ctx.lookup("java:app/myhealthee/SystemAdministrationFacadeBean!ejb.SystemAdministrationFacadeRemote");
		remote.updateCAP(name, location);
	}
	public void  saveDataCap(String mode) throws Exception
	{
		if (mode.equals("edit")) {
			updateDataCap();
		} else {
			addDataCap();
		}
	}
}
