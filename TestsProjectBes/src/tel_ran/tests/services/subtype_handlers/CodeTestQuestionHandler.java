package tel_ran.tests.services.subtype_handlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.subtype_handlers.programming.CodeTester;
import tel_ran.tests.services.utils.FileManagerService;

import java.io.IOException;
import java.util.List;

@Component
public class CodeTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{
	
	
	private static final String LOG = CodeTestQuestionHandler.class.getSimpleName();

	public CodeTestQuestionHandler() {
		super();
		type = ICommonData.QUESTION_TYPE_CODE;
		gradeType = 0;
	}

//	@Override
//	public void analyze() {
//		
//		String pathToAnswersZip;
//		String codeFromPersonPath;
//		CodeTester gradleModule;
//		boolean gradleAnswer;
//		
//		pathToAnswersZip =  getQuestionAttribubes().getFileLocationLink(); //Path to zip		
//		codeFromPersonPath= entityTestQuestion.getAnswer();
//		
//		
//		try {
//			//TODO Check case of time limit of execution of analyze process
//			gradleModule = new CodeTester(FileManagerService.BASE_CODE_TEST);
//			gradleAnswer = gradleModule.testIt(codeFromPersonPath,pathToAnswersZip);
//			if(gradleAnswer){
//				dataObj.setStatus(InnerResultDataObject.STATUS_TRUE);
//			} else {
//				dataObj.setStatus(InnerResultDataObject.STATUS_FALSE);
//			}
//		} catch (IOException e) {
//			//TODO Check case of exception inside of gradle module
//			e.printStackTrace();
//		}
//	}


	
	@Override
	public String getQuestionJson(int index) {
		String stub = getQuestionAttribubes().getAnswers().get(0);
		getQuestionAttribubes().getQuestionId().getQuestionText(); //question text
		getQuestionAttribubes().getDescription(); //description
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuestionViewResultJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getJsonForTest(long eqtId, int index) throws JSONException {
		// from SUPER - text of question, id of EntityTestQuestion, index, type
		// + description and image
		JSONObject result = super.getJsonForTest(eqtId, index);
							
		// get stub
		List<String> list = getQuestionAttribubes().getAnswers();
		if(list!=null && list.size() > 0) {
			result.put(ICommonData.JSN_INTEST_CODE, list.get(0));			
		}
		result.put(ICommonData.JSN_INTEST_DESCRIPTION, getManyLinesField(getQuestionAttribubes().getDescription()));		
		return result;		
	}
	
	protected String preparingDescription(String str) {
		System.out.println(str);	
		String[]lines = str.split("\\n");
		
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < lines.length; i++) {
			result.append(lines[i]).append(System.getProperty("line.separator"));
		
		}
		System.out.println(LOG + " -105-M: preparingDescription : " + result.toString());
		return result.toString();
	}


	@Override
	protected int checkAnswers() {
			
		
		String linkToCode = entityTestQuestion.getAnswer();								
		String pathToAnswerZip = FileManagerService.BASE_DIR_IMAGES + entityQuestionAttributes.getFileLocationLink();
		System.out.println(LOG + " -113-M: checkAnswers - HERE!");
		boolean res;
		int status = 4;
						
		CodeTester tester;
		try {
			tester = new CodeTester();
			res = tester.testIt(linkToCode, pathToAnswerZip);
			
		} catch (IOException e) {
			e.printStackTrace();
			res = false;			
		}
		
		if(res) {
			status = ICommonData.STATUS_CORRECT;
		} else {
			status = ICommonData.STATUS_INCORRECT;
		}
		
		
		return status;
	}

	@Override
	protected String preparingAnswer(String answer) {
		String[]lines = answer.split("\\n");
		
		String linkToCode = null;
		try {
			linkToCode = FileManagerService.saveCode(companyId, testId, questionId, lines);
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		return linkToCode;
	}

	
	// CODE STUB, ANSWER + DATA FROM SUPER + DESCRIPTION
	@Override
	public JSONObject getJsonWithCorrectAnswer(EntityTestQuestions entityTestQuestion) throws JSONException {
		JSONObject result = super.getJsonWithCorrectAnswer(entityTestQuestion);
		
		result.put(ICommonData.JSN_QUESTDET_DESCRIPTION, getManyLinesField(getQuestionAttribubes().getDescription()));
		
		List<String> list = entityQuestionAttributes.getAnswers();
		if(list!=null && list.size() > 0)
			result.put(ICommonData.JSN_QUESTDET_CODE_STUB, list.get(0));
		
		String link = entityTestQuestion.getAnswer();
		if(link!=null && link.length()>3) {			
			List<String> lines = FileManagerService.readFileToList(link);
			if(list!=null && list.size() > 0) {
				JSONArray array = new JSONArray();
				for(String str : lines) {
					JSONObject jsn = new JSONObject();
					jsn.put("line", str);
					array.put(jsn);
				}
				result.put(ICommonData.JSN_QUESTDET_ANSWER, array);
			}
				
		}
		
		return result;
	}

	//impossible to change status for this type of question
	@Override
	protected int getStatusFromMark(String mark) {		
		return -1;
	}
		
	
}