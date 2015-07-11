package tel_ran.tests.services.subtype_handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
@Component
public class AmericanTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{

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
		}
		dataObj.setPersonAnswer(personAnswer);
		analyze();
		return false;
	}

	@Override
	public String getQuestionJson(int index) {
		JSONObject json = new JSONObject();
		try {
			json.put(KEY_INDEX, index);
			json.put(KEY_QUESTION_TEXT, getQuestionAttribubes().getQuestionId().getQuestionText());
			json.put(KEY_QUESTION_IMAGE, getImageBase64(getQuestionAttribubes().getFileLocationLink()));
			json.put(KEY_NUMBER_OF_ANSWERS, getQuestionAttribubes().getNumberOfResponsesInThePicture());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	private String getImageBase64(String fileLocationLink) {
		String res = null;
		byte[] bytes = null;
		FileInputStream file;
		try {
			file = new FileInputStream(fileLocationLink);
			bytes = new byte[file.available()];
			file.read(bytes);
			file.close();
			res = Base64.getEncoder().encodeToString(bytes);
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