package tel_ran.tests.services.subtype_handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
@Component
public class AmericanTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{

	public static final int QUESTION_TYPE = 1;
	
	public AmericanTestQuestionHandler(){
		super();
	}

	@Override
	public void analyze() {
		if(getQuestionAttribubes().getCorrectAnswer().equals(dataObj.getPersonAnswer())){
			dataObj.setStatus(InnerResultDataObject.STATUS_TRUE);
		} else {
			dataObj.setStatus(InnerResultDataObject.STATUS_FALSE);
		}
	}

	@Override
	public boolean setPersonAnswer(JSONObject answerJsonObj) {
		//TODO Export string to constant
		String personAnswer = "";
		try {
			personAnswer = answerJsonObj.getString("answer");
		} catch (JSONException e) {
			System.out.println("answer was not found inside of request");
			return false;
		}
		dataObj.setPersonAnswer(personAnswer);
		analyze();
		return true;
	}

	@Override
	public String getQuestionJson(int index) {
		JSONObject json = new JSONObject();
		try {
			json.put(KEY_QUESTION_TEXT, getQuestionAttribubes().getQuestionId().getQuestionText());
			String fileLink = getQuestionAttribubes().getFileLocationLink();
			json.put(KEY_QUESTION_IMAGE, getImageBase64(fileLink));
			JSONArray answers = new JSONArray();
			for(int max = getQuestionAttribubes().getNumberOfResponsesInThePicture(), i = 0; i < max; i++){
				answers.put(Character.toString ((char) (i+65)));
			}
			json.put(KEY_QUESTION_ANSWERS, answers);
			json.put(KEY_QUESTION_INDEX, index);
			json.put(KEY_QUESTION_TYPE, QUESTION_TYPE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	private String getImageBase64(String fileLocationLink) {
//		imageBase64Text = encodeImage(NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES  + fileLocation);
	//	outArray[1] = "data:image/png;base64," + imageBase64Text; 
		
		String res = null;
		byte[] bytes = null;
		FileInputStream file;
		try {
			String workingDir = System.getProperty("user.dir") + File.separator + tel_ran.tests.services.fields.ApplicationFinalFields.NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES;
			file = new FileInputStream(workingDir+fileLocationLink);
			bytes = new byte[file.available()];
			file.read(bytes);
			file.close();
			res = "data:image/png;base64,"+Base64.getEncoder().encodeToString(bytes);
		} catch (FileNotFoundException e) {	} 
		catch (IOException e) {
		} 
		catch (NullPointerException e) {
		}
		return res;
	}

	@Override
	public String getQuestionViewResultJson() {
		
		// TODO Auto-generated method stub
		return null;
	}
}