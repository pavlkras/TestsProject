package tel_ran.tests.entitys;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.IMaintenanceService;

@Entity
public class EntityTest {

	@Id
	@GeneratedValue
	private long testId; 
	private String testCategory;
	private String testName;
	private String idQuestionsForTheTest; 
	private String password;
	private char[] personAnswers;  
	private char[] correctAnswers;            //letter of the right answer
	private int amountOfCorrectAnswers;
	private int amountOfQuestions;
	private String pictures;           // format to string!! namefoto.jpg,nameAnotherfoto.jpg,xxx.jgg, ...
	private Date testDate;
	private int duration;
	private int complexityLevel;
	private long startTestDate;
	private long endTestDate;
	//
	@ManyToOne
	private EntityCompany entityCompany;
	@ManyToOne
	private EntityPerson entityPerson; 
	//
	public EntityTest() {}
	//
	public JSONObject getJsonObjectCommonData() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("personName",entityPerson.getPersonName());
			jsonObj.put("personSurname",entityPerson.getPersonSurname());
			jsonObj.put("testid",testId);
			jsonObj.put("testCategory",testCategory);
			jsonObj.put("testName", testName);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			jsonObj.put("testDate", df.format(testDate));
		} catch (JSONException e) {}
		return jsonObj;
	}

	public String getJsonDetails() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("duration",duration);
			jsonObj.put("amountOfQuestions",amountOfQuestions);
			jsonObj.put("complexityLevel",complexityLevel);
			jsonObj.put("amountOfCorrectAnswers",amountOfCorrectAnswers);
			jsonObj.put("amountOfWrongAnswers",amountOfQuestions - amountOfCorrectAnswers);
			//TODO Write image encoder into BASE64 
		} catch (JSONException e) {}
		return jsonObj.toString();
	}

	public long getTestId() {
		return testId;
	}

	public String getTestCategory() {
		return testCategory;
	}

	public void setTestCategory(String testCategory) {
		this.testCategory = testCategory;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getIdQuestionsForCreationTest() {
		return idQuestionsForTheTest;
	}

	public void setIdQuestionsForCreationTest(String idQuestionsForTheTest) {
		this.idQuestionsForTheTest = idQuestionsForTheTest;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char[] getPersonAnswers() {
		return personAnswers;
	}

	public void setPersonAnswers(char[] personAnswers) {
		this.personAnswers = personAnswers;
	}	 

	public char[] getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(char[] correctAnswers) {
		this.amountOfQuestions = correctAnswers.length;
		this.correctAnswers = correctAnswers;
	}


	public int getAmountOfCorrectAnswers() {
		return amountOfCorrectAnswers;
	}

	public void setAmountOfCorrectAnswers(int amountOfCorrectAnswers) {
		this.amountOfCorrectAnswers = amountOfCorrectAnswers;
	}

	public int getAmountOfQuestions() {
		return amountOfQuestions;
	}

	public void setAmountOfQuestions(int amountOfQuestions) {
		this.amountOfQuestions = amountOfQuestions;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}	

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getComplexityLevel() {
		return complexityLevel;
	}

	public void setComplexityLevel(int complexityLevel) {
		this.complexityLevel = complexityLevel;
	}

	public long getStartTestDate() {
		return startTestDate;
	}

	public void setStartTestDate(long startTestDate) {
		this.testDate = new Date(startTestDate);
		this.startTestDate = startTestDate;
	}

	public long getEndTestDate() {
		return endTestDate;
	}

	public void setEndTestDate(long endTestDate) {
		this.duration = (int) (endTestDate - this.startTestDate); //// set duration in m_sec
		this.endTestDate = endTestDate;
	}

	public EntityPerson getEntityPerson() {
		return entityPerson;
	}

	public void setEntityPerson(EntityPerson entityPerson) {
		this.entityPerson = entityPerson;
	}

	public EntityCompany getEntityCompany() {
		return entityCompany;
	}

	public void setEntityCompany(EntityCompany entityCompany) {
		this.entityCompany = entityCompany;
	} 

	public void addPictureLink(String pictureLink) { //http-links with delimiters
		if(this.pictures.length() == 0){
			this.pictures = pictureLink;
		}
		else{
			this.pictures += ICommonData.delimiter + pictureLink;
		}
	}
	////
	@Override
	public String toString() {
		return    testId + IMaintenanceService.DELIMITER 
				+ testCategory + IMaintenanceService.DELIMITER
				+ testName + IMaintenanceService.DELIMITER
				+ idQuestionsForTheTest + IMaintenanceService.DELIMITER
				+ password + IMaintenanceService.DELIMITER						
				+ Arrays.toString(personAnswers) + IMaintenanceService.DELIMITER
				+ Arrays.toString(correctAnswers) + IMaintenanceService.DELIMITER
				+ amountOfCorrectAnswers + IMaintenanceService.DELIMITER
				+ amountOfQuestions + IMaintenanceService.DELIMITER		
				+ pictures + IMaintenanceService.DELIMITER
				+ testDate + IMaintenanceService.DELIMITER
				+ duration + IMaintenanceService.DELIMITER
				+ complexityLevel + IMaintenanceService.DELIMITER

				+ startTestDate + IMaintenanceService.DELIMITER	
				+ endTestDate + IMaintenanceService.DELIMITER;
	}

}
