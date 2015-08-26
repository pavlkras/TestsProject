package tel_ran.tests.services.subtype_handlers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityAnswersText;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.subtype_handlers.gradle.CodeTester;
import tel_ran.tests.services.utils.FileManagerService;

import java.io.IOException;
import java.util.List;

@Component
public class CodeTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{
	
	
	public CodeTestQuestionHandler() {
		super();
		type = ICommonData.QUESTION_TYPE_CODE;
	}

	@Override
	public void analyze() {

		String pathToAnswersZip;
		String codeFromPersonPath;
		CodeTester gradleModule;
		boolean gradleAnswer;

		pathToAnswersZip=getQuestionAttribubes().getFileLocationLink(); //Path to zip
		codeFromPersonPath=FileManagerService.getPathToCode(companyId, testId, getQuestionID()); //Path to person code
		try {
			//TODO Check case of time limit of execution of analyze process
			gradleModule = new CodeTester();
			gradleAnswer=gradleModule.testIt(codeFromPersonPath,pathToAnswersZip);
			if(gradleAnswer){
				dataObj.setStatus(InnerResultDataObject.STATUS_TRUE);
			} else {
				dataObj.setStatus(InnerResultDataObject.STATUS_FALSE);
			}
		} catch (IOException e) {
			//TODO Check case of exception inside of gradle module
			e.printStackTrace();
		}
	}


	@Override
	public boolean setPersonAnswer(JSONObject answerJsonObj) {
		// TODO Auto-generated method stub
		// Saving fields to files

		return false;
	}

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
		if(list!=null) {
			result.put(ICommonData.JSN_INTEST_CODE, list.get(0));			
		}
				
		return result;		
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	protected int getStatus(String answer) {
		String[]lines = answer.split("\\n");
		
		String linkToCode = null;
		try {
			linkToCode = FileManagerService.saveCode(companyId, testId, questionId, lines);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		entityTestQuestion.setLinkToAnswer(linkToCode);
		em.merge(entityTestQuestion);
		
		String pathToAnswerZip = entityQuestionAttributes.getFileLocationLink();
		boolean res;
		int status;
		
		CodeTester tester;
		try {
			tester = new CodeTester();
			res = tester.testIt(linkToCode, pathToAnswerZip);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	
	
}