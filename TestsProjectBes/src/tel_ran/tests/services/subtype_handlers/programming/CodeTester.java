package tel_ran.tests.services.subtype_handlers.programming;


import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.GeneratedProgrammingQuestion;
import tel_ran.tests.entitys.InTestQuestion;


public abstract class CodeTester {

	IProgramTestHandler testTemplateHandler;
				
	public static String testTemplatePath = "";
	public static String workFolderPath = "";
		
	public abstract String saveCode(InTestQuestion tQuestion, String[] lines) throws IOException;
	protected abstract IProgramTestHandler getTestHandler();	
		
	public abstract boolean testIt(String codeFromPersonPath, String pathToAnswersZip);
	
	public  abstract void addDataToJson(JSONObject jsn, GeneratedProgrammingQuestion question) throws JSONException;
}