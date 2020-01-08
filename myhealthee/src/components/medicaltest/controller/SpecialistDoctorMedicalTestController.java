package components.medicaltest.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import components.medicaltest.dao.MedicalTestFacadeRemote;
import components.profile.dao.ProfileFacadeRemote;
import entity.enums.TestType;
import entity.imp.MedicalTest;
import entity.imp.Patient;
import entity.imp.SpecialistDoctor;
import services.crud.Operation;
import utils.Messages;
import utils.SessionUtils;

/**
 * Specialist doctor medical test managed bean.
 * 
 * @author adlo
 */
@Named("specialistDoctorMedicalTest")
@SessionScoped
public class SpecialistDoctorMedicalTestController implements Serializable {
	private static final long serialVersionUID = -9177055826994746105L;

	@EJB
	private MedicalTestFacadeRemote ejb;

	@EJB
	private ProfileFacadeRemote ejbProfile;

	/* Fields */
	private Operation mode;
	private SpecialistDoctor specialistDoctor;
	private MedicalTest medicalTest;
	private Patient patient;
	private Part highResImage;
	private List<Patient> listPatients;
	private List<MedicalTest> listMedicalTests;
	private List<TestType> listMedicalTestTypes;

	@PostConstruct
	public void init() {
		this.mode = Operation.NO_OPERATION;
		this.specialistDoctor = (SpecialistDoctor) SessionUtils.getUser();
		this.listAllPatients();
		this.listAllMedicalTestBySpecialistDoctor(this.specialistDoctor);
		this.loadTestTypes();
	}

	public String openModal(Operation operation, Patient patient) {
		this.mode = operation;
		this.medicalTest = new MedicalTest();
		this.patient = patient;
		return null;
	}

	public String openModal(Operation operation, MedicalTest medicalTest) {
		this.mode = operation;
		this.medicalTest = medicalTest;
		this.highResImage = null;
		return null;
	}

	public String closeModal() {
		this.clear();
		return null;
	}

	public String addMedicalTest() {
		medicalTest.setSpecialistDoctor(this.specialistDoctor);
		ejb.addMedicalTest(patient.getUsername(), medicalTest);
		this.clear();
		this.listAllPatients();
		this.listAllMedicalTestBySpecialistDoctor(this.specialistDoctor);
		return null;
	}

	public String updateImage() throws IOException {
		InputStream is = null;
		ByteArrayOutputStream buffer = null;
		try {
			is = highResImage.getInputStream();
			buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			medicalTest.setHighResImage(buffer.toByteArray());
		} catch (IOException e) {
			Messages.addErrorGlobalMessage("The image was imposible to upload, please try again");
			return null;
		} finally {
			if (is != null)
				is.close();
			if (buffer != null)
				buffer.close();
		}
		ejb.addImage(medicalTest.getId(), medicalTest.getHighResImage());
		this.clear();
		this.listAllPatients();
		this.listAllMedicalTestBySpecialistDoctor(this.specialistDoctor);
		return null;
	}
	
	public String removeImage() {
		ejb.removeImage(medicalTest.getId());
		this.clear();
		this.listAllPatients();
		this.listAllMedicalTestBySpecialistDoctor(this.specialistDoctor);
		return null;
	}

	public String deleteMedicalTest() {
		ejb.deleteMedicalTest(medicalTest.getId());
		this.clear();
		this.listAllPatients();
		this.listAllMedicalTestBySpecialistDoctor(this.specialistDoctor);
		return null;
	}

	// Private methods
	private void clear() {
		this.mode = Operation.NO_OPERATION;
		this.patient = null;
		this.medicalTest = null;
		this.highResImage = null;
	}

	private void listAllPatients() {
		this.listPatients = (List<Patient>) ejbProfile.listAllPatients();
	}

	private void listAllMedicalTestBySpecialistDoctor(SpecialistDoctor doctor) {
		this.listMedicalTests = (List<MedicalTest>) ejb.listAllMedicalTestsBySpecialistDoctor(doctor.getUsername());
	}

	private void loadTestTypes() {
		this.listMedicalTestTypes = new ArrayList<TestType>();
		for (TestType tt : TestType.values()) {
			this.listMedicalTestTypes.add(tt);
		}
	}

	// Getters & Setters
	public Operation getMode() {
		return mode;
	}

	public Patient getPatient() {
		return patient;
	}

	public MedicalTest getMedicalTest() {
		return medicalTest;
	}

	public Part getHighResImage() {
		return highResImage;
	}

	public void setHighResImage(Part highResImage) {
		this.highResImage = highResImage;
	}

	public List<Patient> getListPatients() {
		return listPatients;
	}

	public List<MedicalTest> getListMedicalTests() {
		return listMedicalTests;
	}

	public List<TestType> getListMedicalTestTypes() {
		return listMedicalTestTypes;
	}
}
