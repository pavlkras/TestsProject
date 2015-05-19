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

	private int counterStartsOfThisTest = 0;
	////
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
	private String pictures;           // format to string!! namefoto.jpg,nameAnotherfoto.jpg,xxx.jgg, ...
	private int duration;
	private String levelOfDifficulty;
	private long startTestDate = 0L;
	private long endTestDate = 0L;
	//
	@ManyToOne
	private EntityCompany entityCompany;
	@ManyToOne
	private EntityPerson entityPerson;
	@Column(name = "testCodeFromPerson", length = 1000)
	private String testCodeFromPerson; 
	private String resultTestCodeFromPerson; 
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
			jsonObj.put("complexityLevel",levelOfDifficulty);
			jsonObj.put("amountOfCorrectAnswers",amountOfCorrectAnswers);
			jsonObj.put("persentOfRightAnswers",Math.round((float)amountOfCorrectAnswers/(float)amountOfQuestions*100)); // Add calculations from the resultTestCodeFromPerson field  
			jsonObj.put("pictures", getJsonArrayImage(pictures));
			jsonObj.put("codesFromPerson", getJsonArrayCode(testCodeFromPerson, resultTestCodeFromPerson, "java,csharp,cpp,css,"));
		} catch (JSONException e) {}
		return jsonObj.toString();
	}
	
	private JSONArray getJsonArrayImage(String paths){
		JSONArray ar = new JSONArray();
		if(!paths.equals(null)||!paths.equals("")){ 

			String[] pathsArray = paths.split(",");  
			for(String path:pathsArray){					
				JSONObject jsonObj = new JSONObject();		
				String file = getFileWithinString(path);

				if(file != null){
					try {
						jsonObj.put("picture", file);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					ar.put(jsonObj);
				}
			}
		}
		return ar;
	}
	
	private JSONArray getJsonArrayCode(String paths, String codeAnalyseResults, String programmingLanguages){
		JSONArray ar = new JSONArray();
		if(	!paths.equals(null)&&
			!paths.equals("")&&
			!codeAnalyseResults.equals(null)&&
			!codeAnalyseResults.equals("")&&
			!programmingLanguages.equals(null)&&
			!programmingLanguages.equals("")){ 

			String[] pathsArray = paths.split(",");  
			String[] codeAnalyseResultsArray = codeAnalyseResults.split(",");
			String[] programmingLanguagesArray = programmingLanguages.split(",");
			
			if(pathsArray.length == codeAnalyseResultsArray.length && codeAnalyseResultsArray.length == programmingLanguagesArray.length){
				for(int i=0; i<pathsArray.length; i++){
					JSONObject jsonObj = new JSONObject();		
					
					String file = getFileWithinString(pathsArray[i]);

					if(file != null){
						try {
							jsonObj.put("code", file);
							jsonObj.put("codeAnalyseResult", codeAnalyseResultsArray[i]);
							jsonObj.put("programmingLanguage", programmingLanguagesArray[i]);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						ar.put(jsonObj);
					}
				}
			}
		}
		return ar;
	}

	public String getFileWithinString(String path){
		String res = null;
		BufferedReader in=null;
		try {
			in = new BufferedReader(new FileReader(path));
			String line;
			StringBuffer stringBuffWithFileContent = new StringBuffer();
			while((line = in.readLine()) != null){
				stringBuffWithFileContent.append(line);
				stringBuffWithFileContent.append("\n");
			}
			res = stringBuffWithFileContent.toString();
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

	public String getLevelOfDifficulty() {
		return levelOfDifficulty;
	}

	public void setLevelOfDifficulty(String complexityLevel) {
		this.levelOfDifficulty = complexityLevel;
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
	public void setResultTestingCodeFromPerson(String resultTestCodeFromPerson) {
		this.resultTestCodeFromPerson = resultTestCodeFromPerson;

	}
	public String getResultTestingCodeFromPerson() {
		return resultTestCodeFromPerson;		
	}
	public int getCounterPicturesOfTheTest() {
		return counterStartsOfThisTest;
	}
	public void setCounterPicturesOfTheTest(int counterStartsTheTest) {
		this.counterStartsOfThisTest = counterStartsTheTest;
	}
	
	public String getTestCodeFromPerson() {
		return testCodeFromPerson;
	}
	public void setTestCodeFromPerson(String testCodeFromPerson) {
		this.testCodeFromPerson = testCodeFromPerson;
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
				+ levelOfDifficulty + IMaintenanceService.DELIMITER

				+ startTestDate + IMaintenanceService.DELIMITER	
				+ endTestDate + IMaintenanceService.DELIMITER;
	}
}
