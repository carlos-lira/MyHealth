package utils;

/**
 * Query names identifiers.
 * 
 * @author adlo
 */
public final class QueryNames {

	/**
	 * Hide constructor.
	 */
	private QueryNames() {
		// nop
	}
	
	// User query names
	public static final String GET_ALL_USERS = "getAllUsers";
	public static final String GET_USER = "getUser";
	
	// Patient query names
	public static final String GET_ALL_PATIENTS = "getAllPatients";
	
	// Family doctor queries
	public static final String GET_ALL_FAMILY_DOCTORS = "getAllFamilyDoctors";
	public static final String GET_ALL_FAMILY_DOCTORS_BY_CAP = "getAllFamilyDoctorsByCap";
	
	// Specialist doctor query names
	public static final String GET_ALL_SPECIALIST_DOCTORS = "getAllSpecialistDoctors";
	public static final String GET_ALL_SPECIALIST_DOCTORS_BY_MEDICAL_SPECIALTY = "getAllSpecialistDoctorsByMedicalSpecialty";
	
	// Administrator query names
	public static final String GET_ALL_ADMINISTRATORS = "getAllAdministrators";
	
	// PrimaryHealthCareCenter query names
	public static final String GET_ALL_PRIMARY_HELTHCARE_CENTERS = "getAllPrimaryHealthCareCenters";
	public static final String GET_PRIMARY_HEALTHCARE_CENTER_BY_NAME = "getPrimaryHealthCareCenterByName";

	// MedicalSpeciality query names
	public static final String GET_ALL_MEDICAL_SPECIALITIES = "getAllMedicalSpecialities";
	public static final String GET_MEDICAL_SPECIALITY_BY_NAME = "getMedicalSpecialityByName";
	
	// Medical tests
	public static final String GET_ALL_MEDICAL_TESTS = "getAllMedicalTests";
	public static final String GET_MEDICAL_TEST_BY_ID = "getMedicalTestById";
	public static final String GET_ALL_MEDICAL_TESTS_BY_SPECIALIST_DOCTOR = "getAllMedicalTestsBySpecialistDoctor";
	
	// Questions query names
	public static final String GET_ALL_QUESTIONS = "getAllQuestions";
	public static final String GET_ALL_QUESTIONS_BY_STATUS = "getAllQuestionsByStatus";
	public static final String GET_QUESTION_BY_ID = "getQuestionById";
}
