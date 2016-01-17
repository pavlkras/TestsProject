package tel_ran.tests.services.subtype_handlers;


import tel_ran.tests.entitys.InTestQuestion;

public class SingleTestQuestionHandlerFactory {

	
	public static ITestQuestionHandler getInstance(InTestQuestion etq) {
//		EntityQuestionAttributes eqa = etq.getEntityQuestionAttributes();
//		Test test = etq.getTest();
//		String metaCategory = eqa.getMetaCategory();
//		String category1 = eqa.getCategory1();
//		ITestQuestionHandler result;
//		if(metaCategory!=null) {			
//			if(metaCategory.equals(TestProcessor.MC_PROGRAMMING)) {			
//				result = getTestQuestionHandler(metaCategory, category1); 
//			} else {
//				result = getTestQuestionHandler(metaCategory);
//			}			
//			result.setEntityQuestionAttributes(eqa);
//			result.setCompanyId(eqa.getEntityCompany().getId());
//			result.setEtqId(etq.getId());
//			result.setTestId(test.getId());
//			return result;
//		}				
		return null;				
	}
	
	

}