package tel_ran.tests.services.subtype_handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.gradle.jarjar.org.apache.commons.lang.NullArgumentException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import tel_ran.tests.data_loader.TestsPersistence;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.utils.FileManagerService;

public abstract class AbstractTestQuestionHandler extends TestsPersistence implements ITestQuestionHandler {
			
	private static final String LOG = AbstractTestQuestionHandler.class.getSimpleName();
	protected EntityQuestionAttributes entityQuestionAttributes;
	protected EntityTestQuestions entityTestQuestion;
	protected long questionId = -1;
	protected long etqId;
//	protected InnerResultDataObject dataObj;
	protected long companyId;
	protected long testId;
	
	int type; // type to display questions for FES
	int gradeType; //type of grade (correct/incorrect, 1-2-3-4-5, percent...)
	
				
	public void setEtqId(long etqId) {
		this.etqId = etqId;
		this.entityTestQuestion = em.find(EntityTestQuestions.class, etqId);
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

//	final public void fromJsonString(String json, long companyId, long testId) {
//		dataObj = new Gson().fromJson(json, InnerResultDataObject.class);
//		this.companyId = companyId;
//		this.testId = testId;
//	}

//	final public void createFromQuestion(long questionId, String metacategory) {
//		dataObj = new InnerResultDataObject();
//		dataObj.setQuestionID(questionId);
//		dataObj.setMetacategory(metacategory);
//		dataObj.setStatus(InnerResultDataObject.STATUS_NOT_ASKED);		
//	}
	
//	final public String toJsonString() {
//		return new Gson().toJson(dataObj);
//	}

//	final public long getQuestionID() {
//		if(dataObj!=null)
//			return dataObj.getQuestionID();
//		else
//			return questionId;
//	}

//	final public String getStatus() {
//		return dataObj.getStatus();
//	}
	
	public EntityQuestionAttributes getQuestionAttribubes() {
		if(entityQuestionAttributes==null) {
			System.out.println(LOG + " - !!! NO ENTITY QUESTION ATTRIBUTES for this Handler!");
			if(questionId!=-1)
				entityQuestionAttributes = em.find(EntityQuestionAttributes.class, questionId);				
		}
		return entityQuestionAttributes;
	}
	
	@Override
	public JSONObject getJsonForTest(long eqtId, int index) throws JSONException {
		JSONObject result = new JSONObject();
		result.put(ICommonData.JSN_INTEST_QUESTION_TEXT, getQuestionAttribubes().getEntityTitleQuestion().getQuestionText());
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

	
	// METACATEGORY, CATEGORY, TEXT, TYPE, ANSWER OF PERSON, GRADE_TYPE, GRADE_OPTIONS (for nonchecked questions)
	// QUESTION_ID, STATUS
	@Override
	public JSONObject getJsonWithCorrectAnswer(EntityTestQuestions entityTestQuestion) throws JSONException {
		if(entityQuestionAttributes==null) 
			throw new NullArgumentException("entityQuestionAttributes");
		JSONObject jsn = new JSONObject();
		try {
			jsn.put(ICommonData.JSN_QUESTDET_QUESTION_ID, entityTestQuestion.getId());
			int status = entityTestQuestion.getStatus();
			jsn.put(ICommonData.JSN_QUESTDET_STATUS_NUM, status);
			jsn.put(ICommonData.JSN_QUESTDET_STATUS_STR, IPublicStrings.QUESTION_STATUS[status]);
			jsn.put(ICommonData.JSN_QUESTDET_METACATEGORY, entityQuestionAttributes.getMetaCategory());
			jsn.put(ICommonData.JSN_QUESTDET_CATEGORY1, entityQuestionAttributes.getCategory1());
			jsn.put(ICommonData.JSN_QUESTDET_TEXT, entityQuestionAttributes.getEntityTitleQuestion().getQuestionText());
			jsn.put(ICommonData.JSN_QUESTDET_TYPE, this.getType());
			jsn.put(ICommonData.JSN_QUESTDET_ANSWER, getAnswerInJSON(entityTestQuestion));
			jsn.put(ICommonData.JSN_QUESTDET_GRADE_TYPE, this.gradeType);
			putOptionsForCheck(status, ICommonData.JSN_QUESTDET_GRADE_OPTIONS, jsn);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return jsn;
	}
	
	
	
	protected void putOptionsForCheck(int status, String key, JSONObject jsn) throws JSONException {
		if(status == ICommonData.STATUS_UNCHECKED) {
			JSONArray result = new JSONArray();
			String[] options = IPublicStrings.GRADE_OPTIONS[this.gradeType];
			if(options!=null && options.length > 0)
				for (String str : options) {
					result.put(str);
				}
			jsn.put(key, result);
		}
		
	}

	protected JSONArray getAnswerInJSON(EntityTestQuestions etq) {
		return this.getManyLinesField(etq.getAnswer());
	}
	


	protected void putImageForJSON(String key, JSONObject jsn) throws JSONException {
		String fileLink = getQuestionAttribubes().getFileLocationLink();
		if(fileLink!=null && fileLink.length()>2)
			jsn.put(key, getImageBase64(fileLink));
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public int setMark(String mark) {
		int newStatus = getStatusFromMark(mark);
		
		//if the type of the question doesn't allow to change status 
		// newStatus will be -1
		if(newStatus!=-1) {
			entityTestQuestion.setStatus(newStatus);
			em.merge(entityTestQuestion);
		}
		return newStatus;
	}
	
	abstract protected int getStatusFromMark(String mark);
}
