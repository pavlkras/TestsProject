package tel_ran.tests.services.subtype_handlers;

import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.utils.SpringApplicationContext;

public class SingleTestQuestionHandlerFactory {
//	@SuppressWarnings("serial")
//	private static final Map <String, String> map = new HashMap<String, String>(){
//	{
//		put("Abstract_Reasoning", "tel_ran.tests.services.subtype_handlers.AmericanTestQuestionHandler");
//		put("Attention_Test", "tel_ran.tests.services.subtype_handlers.AmericanTestQuestionHandler");
//		put("Quantative_Reasoning", "tel_ran.tests.services.subtype_handlers.AmericanTestQuestionHandler");
//		put("Programming_Task", "tel_ran.tests.services.subtype_handlers.CodeTestQuestionHandler");
//		put("Text", "tel_ran.tests.services.subtype_handlers.TextTestQuestionHandler");
//	}};
	
//	public static ITestQuestionHandler getInstance(EntityQuestionAttributes question){
//		String metaCategory = question.getMetaCategory();
//		ITestQuestionHandler testQuestionResult = getTestQuestionHandler(metaCategory);
//		
//		testQuestionResult.createFromQuestion(question.getId(), metaCategory);
//		return testQuestionResult;
//	}

	//Restoring ITestQuestionHandler from single question json
//	public static ITestQuestionHandler getInstance(JSONObject singleQuestion, long companyId, long testId){
//		String metaCategory = null;
//		try {
//			metaCategory = singleQuestion.getString(InnerResultDataObject.KEY_METACATEGORY);			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		ITestQuestionHandler testQuestionHandler = null;
//		if(metaCategory != null){
//			testQuestionHandler = getTestQuestionHandler(metaCategory);
//			testQuestionHandler.fromJsonString(singleQuestion.toString(), companyId, testId);
//		}
//		return testQuestionHandler;
//	}

	public static ITestQuestionHandler getInstance(EntityTestQuestions etq) {
		String metaCategory = etq.getEntityQuestionAttributes().getMetaCategory();
		if(metaCategory!=null) {
			ITestQuestionHandler result = getTestQuestionHandler(metaCategory);
			result.setEntityQuestionAttributes(etq.getEntityQuestionAttributes());
			result.setCompanyId(etq.getEntityTest().getEntityCompany().getId());
			result.setEtqId(etq.getId());
			result.setTestId(etq.getEntityTest().getTestId());
			return result;
		}				
		return null;
				
	}
	
	private static ITestQuestionHandler getTestQuestionHandler(String metaCategory) {
		if(!metaCategory.equals(IPublicStrings.COMPANY_AMERICAN_TEST) && !metaCategory.equals(IPublicStrings.COMPANY_QUESTION))
			metaCategory = TestProcessor.getMetaCategoryKeyByPublicName(metaCategory);
		return (ITestQuestionHandler) SpringApplicationContext.getBean(metaCategory);
//		try {
//			return (ITestQuestionHandler) Class.forName(map.get(metaCategory)).newInstance();
//		} catch (InstantiationException | IllegalAccessException
//				| ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return null;
	}
}