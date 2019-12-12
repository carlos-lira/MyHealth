package utils;

/**
 * Query names identifiers.
 * 
 * @author adlo
 */
public abstract interface QueryNames {

	// Administrator query names
	public static final String GET_ALL_ADMINISTRATORS = "getAllAdministrators";
	public static final String GET_ADMINISTRATOR = "getAdministrator";
	
	
	// PrimaryHealthCareCenter query names
	public static final String GET_ALL_PRIMARY_HELTHCARE_CENTERS = "getAllPrimaryHealthCareCenters";
	public static final String GET_PRIMARY_HEALTHCARE_CENTER_BY_NAME = "getPrimaryHealthCareCenterByName";

	// MedicalSpeciality query names
	public static final String GET_ALL_MEDICAL_SPECIALITIES = "getAllMedicalSpecialities";
	public static final String GET_MEDICAL_SPECIALITY_BY_NAME = "getMedicalSpecialityByName";
}
