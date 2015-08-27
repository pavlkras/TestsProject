package tel_ran.tests.services.subtype_handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.TestsPersistence;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.utils.FileManagerService;

public abstract class AbstractTestQuestionHandler extends TestsPersistence implements ITestQuestionHandler {
			
	private static final String LOG = AbstractTestQuestionHandler.class.getSimpleName();
	protected EntityQuestionAttributes entityQuestionAttributes;
	protected EntityTestQuestions entityTestQuestion;
	protected long questionId;
	protected long etqId;
	protected InnerResultDataObject dataObj;
	protected long companyId;
	protected long testId;
	int type;
	
				
	public void setEtqId(long etqId) {
		this.etqId = etqId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setTestId(long testId) {
		this.testId = testId;
	}

	public void setEntityQuestionAttributes(
			EntityQuestionAttributes entityQuestionAttributes) {
		this.entityQuestionAttributes = entityQuestionAttributes;
		this.questionId = entityQuestionAttributes.getId();
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	final public void fromJsonString(String json, long companyId, long testId) {
		dataObj = new Gson().fromJson(json, InnerResultDataObject.class);
		this.companyId = companyId;
		this.testId = testId;
	}

	final public void createFromQuestion(long questionId, String metacategory) {
		dataObj = new InnerResultDataObject();
		dataObj.setQuestionID(questionId);
		dataObj.setMetacategory(metacategory);
		dataObj.setStatus(InnerResultDataObject.STATUS_NOT_ASKED);		
	}
	
	final public String toJsonString() {
		return new Gson().toJson(dataObj);
	}

	final public long getQuestionID() {
		if(dataObj!=null)
			return dataObj.getQuestionID();
		else
			return questionId;
	}

	final public String getStatus() {
		return dataObj.getStatus();
	}
	
	public EntityQuestionAttributes getQuestionAttribubes() {
		if(entityQuestionAttributes == null){
			entityQuestionAttributes = em.find(EntityQuestionAttributes.class, getQuestionID());
		}
		return entityQuestionAttributes;
	}
	
	@Override
	public JSONObject getJsonForTest(long eqtId, int index) throws JSONException {
		JSONObject result = new JSONObject();
		result.put(ICommonData.JSN_INTEST_QUESTION_TEXT, getQuestionAttribubes().getQuestionId().getQuestionText());
		result.put(ICommonData.JSN_INTEST_QUESTION_ID, eqtId);
		
		// status
		result.put(ICommonData.JSN_INTEST_INDEX, index);
		
		//type of show question = 0 (only image)
		result.put(ICommonData.JSN_INTEST_TYPE, getType());
		return result;		
	}
	
	protected int getType() {
		return type;
	};
	
	protected String getImageBase64(String fileLocationLink) {
//		imageBase64Text = encodeImage(NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES  + fileLocation);
	//	outArray[1] = "data:image/png;base64," + imageBase64Text; 
		
		String res = null;
		byte[] bytes = null;
		FileInputStream file;
		try {
			String workingDir = FileManagerService.BASE_DIR_IMAGES;
			file = new FileInputStream(workingDir+fileLocationLink);
			System.out.println(workingDir+fileLocationLink);
			bytes = new byte[file.available()];
			file.read(bytes);
			file.close();
			res = "data:image/jpeg;base64,"+Base64.getEncoder().encodeToString(bytes);
		} catch (FileNotFoundException e) {	} 
		catch (IOException e) {
		} 
		catch (NullPointerException e) {
		}
		return res;
	}
	
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public boolean setPersonAnswer(JSONObject answerJsonObj, long etqId) {
		boolean res;		
		entityTestQuestion = em.find(EntityTestQuestions.class, etqId);
		if(entityTestQuestion!=null) {
			String answer;
			try {
				answer = answerJsonObj.getString(ICommonData.JSN_INTEST_ANSWER);
				int status = ICommonData.STATUS_UNCHECKED;			
				
				String answerToSave = preparingAnswer(answer);
				entityTestQuestion.setAnswer(answerToSave);
				entityTestQuestion.setStatus(status);
				em.merge(entityTestQuestion);
				
				res = true;
			} catch (JSONException e) {				
				e.printStackTrace();
				res = false;
			}			
						
		} else {
			res = false;
		}
				
		return res;
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public int checkResult() {
		entityTestQuestion = em.find(EntityTestQuestions.class, etqId);
		int result = checkAnswers();
		System.out.println(LOG + " -172-M: checkResult - result = " + result);
		entityTestQuestion.setStatus(result);
		em.merge(entityTestQuestion);
		return result;
		
	}
	
	protected JSONArray getManyLinesField(String str) {
		String[] lines = str.split("\n");
		String[] lines2 = str.split(System.getProperty("line.separator"));
			
		JSONArray result = new JSONArray();
		
		List<String> lst = new ArrayList<>();
		for(String s : lines) {
			JSONObject jsn = new JSONObject();
			try {
				
				s = s.replaceAll("\\t", "    ");
				jsn.put("line", s);
				result.put(jsn);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			lst.add(s);
		}
		
		return result;
		
		
	}
	
	protected abstract int checkAnswers();
	protected abstract String preparingAnswer(String answer);


}