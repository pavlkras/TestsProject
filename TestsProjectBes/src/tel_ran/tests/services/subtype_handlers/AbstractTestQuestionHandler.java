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
import tel_ran.tests.entitys.InTestQuestion;
import tel_ran.tests.entitys.Question;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.utils.FileManagerService;
import tel_ran.tests.services.utils.SpringApplicationContext;

public abstract class AbstractTestQuestionHandler implements ITestQuestionHandler {
			
		
	protected long questionId = -1;
	protected InTestQuestion tQuestion;
	protected long companyId;
	protected long testId;
	
	protected String categoryType;
	
	int type; // type to display questions for FES
	int gradeType; //type of grade (correct/incorrect, 1-2-3-4-5, percent...)
	
	public abstract void printQuestion();

	public void setTestQuestion(InTestQuestion tQuestion) {
		this.tQuestion = tQuestion;
	}
	
	
	
	public String getCategoryType() {
		return categoryType;
	}

	public abstract void setQuestion(Question question);
	
	
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setTestId(long testId) {
		this.testId = testId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	
	
	@Override
	public String getJsonForTest(int index) throws JSONException {
		JSONObject result = new JSONObject();		
		result.put(ICommonData.JSN_INTEST_QUESTION_ID, tQuestion.getId());				
		result.put(ICommonData.JSN_INTEST_INDEX, index);
		
		//type of show question = 0 (only image)
		result.put(ICommonData.JSN_INTEST_TYPE, getType());
		
		addDataToJson(result);
		
		return result.toString();		
	}
	
	protected abstract void addDataToJson(JSONObject jsn) throws JSONException;
	
	protected int getType() {
		System.out.println(type);
		return type;
	};
	
	protected String getImageBase64(String fileLocationLink) {
//		imageBase64Text = encodeImage(NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES  + fileLocation);
	//	outArray[1] = "data:image/png;base64," + imageBase64Text; 
		
		String res = "";
		if(fileLocationLink==null || fileLocationLink.length()<3) return res;
		
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
	public InTestQuestion setPersonAnswer(JSONObject answerJsonObj, InTestQuestion tQuestion) throws JSONException, IOException {
		
		this.tQuestion = tQuestion;	
		String answer = answerJsonObj.getString(ICommonData.JSN_INTEST_ANSWER);
								
		String answerToSave = preparingAnswer(answer);
			
		tQuestion.setAnswer(answerToSave);
		tQuestion.setStatus(ICommonData.STATUS_UNCHECKED);
					
		return tQuestion;
		
	}
	
	
	
	@Override	
	public int checkResult() {		
		int result = checkAnswers();		
		tQuestion.setStatus(result);		
		return result;
		
	}
	
	protected JSONArray getManyLinesField(String str) {
		String[] lines = str.split("\n");
			
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
	protected abstract String preparingAnswer(String answer) throws IOException;

	
	// METACATEGORY, CATEGORY, TEXT, TYPE, ANSWER OF PERSON, GRADE_TYPE, GRADE_OPTIONS (for nonchecked questions)
	// QUESTION_ID, STATUS
	@Override
	public JSONObject getJsonWithCorrectAnswer(InTestQuestion entityTestQuestion) throws JSONException {
	
		JSONObject jsn = new JSONObject();
		try {
			jsn.put(ICommonData.JSN_QUESTDET_QUESTION_ID, entityTestQuestion.getId());
			int status = entityTestQuestion.getStatus();
			jsn.put(ICommonData.JSN_QUESTDET_STATUS_NUM, status);
			jsn.put(ICommonData.JSN_QUESTDET_STATUS_STR, IPublicStrings.QUESTION_STATUS[status]);
						
			jsn.put(ICommonData.JSN_QUESTDET_TYPE, this.getType());
			jsn.put(ICommonData.JSN_QUESTDET_ANSWER, getAnswerInJSON(entityTestQuestion));
			jsn.put(ICommonData.JSN_QUESTDET_GRADE_TYPE, this.gradeType);
			
			addFullDataToJson(jsn);
			
			putOptionsForCheck(status, ICommonData.JSN_QUESTDET_GRADE_OPTIONS, jsn);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return jsn;
	}
	
	protected abstract void addFullDataToJson(JSONObject jsn) throws JSONException;
	
	
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

	protected JSONArray getAnswerInJSON(InTestQuestion etq) {
		return this.getManyLinesField(etq.getAnswer());
	}
	

	public static ITestQuestionHandler handlerFactory(String beanName) {
		
		try {
			ITestQuestionHandler result = (ITestQuestionHandler)SpringApplicationContext.getBean(beanName);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	
	
	@Override
	public int setMark(String mark) {
		int newStatus = getStatusFromMark(mark);
		
		if(newStatus!=-1) {
			tQuestion.setStatus(newStatus);			
		}
		return newStatus;
	}
	
	abstract protected int getStatusFromMark(String mark);
}
