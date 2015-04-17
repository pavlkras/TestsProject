package tel_ran.tests.entitys;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.json.JSONArray;
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
	@Column(name = "pictures", length = 500)
	private String pictures="";           // format to string!! namefoto.jpg,nameAnotherfoto.jpg,xxx.jgg, ...
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
	public JSONObject getJsonObjectCommonData(String timeZone) {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("personName",entityPerson.getPersonName());
			jsonObj.put("personSurname",entityPerson.getPersonSurname());
			jsonObj.put("testid",testId);
			jsonObj.put("testCategory",testCategory);
			jsonObj.put("testName", testName);
			SimpleDateFormat sdf = new SimpleDateFormat(ICommonData.DATE_FORMAT);
			sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
			jsonObj.put("testDate", sdf.format(new Date(startTestDate)));
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
			jsonObj.put("persentOfRightAnswers",Math.round((float)amountOfCorrectAnswers/(float)amountOfQuestions*100));
			JSONArray ar = new JSONArray();
			if(!pictures.equals("")){ 
				
				String[] picturePaths = pictures.split(",");  
				for(String path:picturePaths){					
					JSONObject pic = new JSONObject();
					////
					//String workingDir = System.getProperty("user.dir").replaceAll("\\\\", "/");
					//String replacedText = path.replaceAll("\\\\", "/");
					//String imageLink = workingDir + "/" + replacedText.replaceAll(" ", "");
					////
					//String picture = getPictureBase64(imageLink);
					//if(picture != null){
					//	pic.put("picture", getPictureBase64(imageLink));
					//	ar.put(pic);
					//}
					String picture = getPictureBase64(path);
					if(picture != null){
						pic.put("picture", picture);
						ar.put(pic);
					}
				}
			}
		 	jsonObj.put("pictures", ar); 
		} catch (JSONException e) {}
		return jsonObj.toString();
	}
	
	public String getPictureBase64(String pathToPicture){
		String res = null;
		BufferedReader in=null;
		try {
			in = new BufferedReader(new FileReader(pathToPicture));
			String line;
			StringBuffer pictureBase64 = new StringBuffer();
			while((line = in.readLine()) != null){
				pictureBase64.append(line);
			}
			res = pictureBase64.toString();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally{
			try {
				in.close();
			} catch (Exception e) {}
		}
		return res;
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
				+ duration + IMaintenanceService.DELIMITER
				+ complexityLevel + IMaintenanceService.DELIMITER

				+ startTestDate + IMaintenanceService.DELIMITER	
				+ endTestDate + IMaintenanceService.DELIMITER;
	}

}
