package tel_ran.tests.services.subtype_handlers;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;

public class SingleTestQuestionHandlerFactory {
	@SuppressWarnings("serial")
	private static final Map <String, String> map = new HashMap<String, String>(){
	{
		put("Abstract_Reasoning", "tel_ran.tests.services.subtype_handlers.AmericanTestQuestionHandler");
		put("Attention_Test", "tel_ran.tests.services.subtype_handlers.AmericanTestQuestionHandler");
		put("Quantative_Reasoning", "tel_ran.tests.services.subtype_handlers.AmericanTestQuestionHandler");
		put("Programming_Task", "tel_ran.tests.services.subtype_handlers.CodeTestQuestionHandler");
		put("Text", "tel_ran.tests.services.subtype_handlers.TextTestQuestionHandler");
	}};
	
	
	public static ITestQuestionHandler getInstance(EntityQuestionAttributes question){
		String metaCategory = question.getMetaCategory();
		ITestQuestionHandler testQuestionResult = getTestQuestionHandler(metaCategory);
		testQuestionResult.createFromQuestion(question);
		return testQuestionResult;
	}
	
	public static ITestQuestionHandler getInstance(JSONObject singleQuestion){
		String metaCategory = null;
		try {
			metaCategory = singleQuestion.getString(InnerResultDataObject.KEY_METACATEGORY);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ITestQuestionHandler testQuestionHandler = null;
		if(metaCategory != null){
			testQuestionHandler = getTestQuestionHandler(metaCategory);
		}
		// Creation of new instance with filling it
		Gson gson = new Gson();
		ITestQuestionHandler res = gson.fromJson(singleQuestion.toString(), testQuestionHandler.getClass());
		
		return res;
	}

	private static ITestQuestionHandler getTestQuestionHandler(String metaCategory) {

		try {
			return (ITestQuestionHandler) Class.forName(map.get(metaCategory)).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}