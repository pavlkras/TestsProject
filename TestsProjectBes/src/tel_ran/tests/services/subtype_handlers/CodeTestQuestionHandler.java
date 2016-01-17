package tel_ran.tests.services.subtype_handlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import tel_ran.tests.entitys.GeneratedProgrammingQuestion;
import tel_ran.tests.entitys.Question;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.subtype_handlers.programming.CodeTester;
import tel_ran.tests.services.utils.FileManagerService;

import java.io.IOException;
import java.util.List;

@Component
public class CodeTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{
		
	CodeTester codeTester;
	GeneratedProgrammingQuestion question;
	
	
	private static final String LOG = CodeTestQuestionHandler.class.getSimpleName();

	public CodeTestQuestionHandler() {
		super();	
		type = ICommonData.QUESTION_TYPE_CODE;
		gradeType = 0;
		categoryType = "auto";
	}	
	
	
	
	public void setCodeTester(CodeTester codeTester) {
		this.codeTester = codeTester;
	}



	@Override
	public void setQuestion(Question question) {
		this.question = (GeneratedProgrammingQuestion)question;
		type = ICommonData.QUESTION_TYPE_CODE;
		gradeType = 0;
	}	
	
	@Override
	protected void addDataToJson(JSONObject jsn) throws JSONException {
		System.out.println("question null??? ");
		System.out.println(question==null);
		System.out.println("title what? ");
		System.out.println(question.getTitle()==null);		
		jsn.put(ICommonData.JSN_INTEST_QUESTION_TEXT, question.getTitle().getQuestionText());
		jsn.put(ICommonData.JSN_INTEST_DESCRIPTION, getManyLinesField(question.getDescription()));	
		codeTester.addDataToJson(jsn, question);
		
	}
	
	@Override
	protected void addFullDataToJson(JSONObject jsn) throws JSONException {
		
		jsn.put(ICommonData.JSN_QUESTDET_METACATEGORY, question.getCategory().getMetaCategory());
		jsn.put(ICommonData.JSN_QUESTDET_CATEGORY1, question.getCategory().getCategory1());
		jsn.put(ICommonData.JSN_QUESTDET_TEXT, question.getCategory().getCategory2());
		
		jsn.put(ICommonData.JSN_QUESTDET_DESCRIPTION, getManyLinesField(question.getDescription()));
		
		codeTester.addDataToJson(jsn, question);
			
		String link = tQuestion.getAnswer();
		if(link!=null && link.length()>3) {			
			List<String> lines = FileManagerService.readFileToList(link);
			if(lines!=null && lines.size() > 0) {
				JSONArray array = new JSONArray();
				for(String str : lines) {
					JSONObject jsn2 = new JSONObject();
					jsn2.put("line", str);
					array.put(jsn2);
				}
				jsn.put(ICommonData.JSN_QUESTDET_ANSWER, array);
			}
				
		}
		
	}
				
		
	@Override
	public String getQuestionJson(int index) {
//		String stub = getQuestionAttribubes().getAnswers().get(0);
//		getQuestionAttribubes().getEntityTitleQuestion().getQuestionText(); //question text
//		getQuestionAttribubes().getDescription(); //description
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuestionViewResultJson() {
		// TODO Auto-generated method stub
		return null;
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
				
		String linkToCode = tQuestion.getAnswer();	
		
		String pathToAnswerZip = FileManagerService.BASE_DIR_IMAGES + question.getFileLocationLink();
		
		boolean res = codeTester.testIt(linkToCode, pathToAnswerZip);
		int status = 4;				
		
		if(res) {
			status = ICommonData.STATUS_CORRECT;
		} else {
			status = ICommonData.STATUS_INCORRECT;
		}
				
		return status;
	}

	@Override
	protected String preparingAnswer(String answer) throws IOException {
		String[]lines = answer.split("\\n");
		
		String linkToCode = this.codeTester.saveCode(this.tQuestion, lines);
		
		return linkToCode;
	}

	
	
	//impossible to change status for this type of question
	@Override
	protected int getStatusFromMark(String mark) {		
		return -1;
	}

	@Override
	public void printQuestion() {
		System.out.println(this.question.getId());
		
	}
	
}