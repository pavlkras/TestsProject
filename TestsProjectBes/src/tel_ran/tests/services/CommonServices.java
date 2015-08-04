package tel_ran.tests.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.persistence.Query;

import tel_ran.tests.entitys.EntityAnswersText;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.services.interfaces.ICommonService;

public abstract class CommonServices extends TestsPersistence implements ICommonService {

	protected List<String> getQuery(String query) {
		Query q = em.createQuery(query);
		List<String> allCategories = q.getResultList();		
		return allCategories;
	}
	
	protected String getLimitsForQueryWithWhere() {
		if(getLimitsForQuery()==null)
			return null;
		else
			return " WHERE" + getLimitsForQuery();
	}
	
	protected String getLimitsForQueryWithAND() {
		if(getLimitsForQuery()==null)
			return null;
		else
			return " AND" + getLimitsForQuery();
	}
	
	abstract protected String getLimitsForQuery();
			
	public List<String> getAllCategories1FromDataBase() {
		String query = "Select DISTINCT q.category1 FROM EntityQuestionAttributes q" + getLimitsForQueryWithWhere() + " ORDER BY q.category1";
		return getQuery(query);
	}

	
	public List<String> getAllCategories2FromDataBase() {
		String query = "Select DISTINCT q.category2 FROM EntityQuestionAttributes q" + getLimitsForQueryWithWhere() + " ORDER BY q.category2";
		return getQuery(query);
	}
	
	public List<String> getAllMetaCategoriesFromDataBase() {
		String query = "Select DISTINCT q.metaCategory FROM EntityQuestionAttributes q" + getLimitsForQueryWithWhere() + " ORDER BY q.metaCategory";
		return getQuery(query);
	}	
	
	protected String getQuestionWithDelimeters(EntityQuestionAttributes eqa) {
		StringBuilder result = new StringBuilder("");
		result.append(eqa.getId()).append(DELIMITER); // - 0 = id
		result.append(eqa.getQuestionId().getQuestionText()).append(DELIMITER); // - 1 = question
		result.append(eqa.getDescription()).append(DELIMITER); // - 2 = description
		result.append(eqa.getCategory1()).append(DELIMITER);  // - 4 = lang cod	and company's categories
		result.append(eqa.getCorrectAnswer()).append(DELIMITER); // - 5 = correct answer (letter or number)
		result.append(eqa.getNumberOfResponsesInThePicture()).append(DELIMITER); // - 6 = num answers on picture
		result.append(eqa.getMetaCategory()).append(DELIMITER); // - 7 = meta category 
		result.append(getAnswers(eqa));  // optionaly 8-12 = variants of answers or stubs for ProgrammingTasks               
		
		return result.toString();
	}
	
	protected String getAnswers(EntityQuestionAttributes eqa) {
		List<EntityAnswersText> anRes;
		StringBuilder outRes = new StringBuilder("");
		if(eqa.getQuestionAnswersList() != null){					
			anRes = eqa.getQuestionAnswersList();
			for(EntityAnswersText rRes :anRes){
				outRes.append(rRes.getAnswerText()).append(DELIMITER);
			}
		}
		return outRes.toString();
	}
	
	@Override
	public String[]  getQuestionById(String questionID, int actionKey) {// getting question attributes by ID !!!!!!!!!!!!!!!!!!!!!!!!!  
		String[] outArray = new String[4];
		StringBuilder  outRes = new StringBuilder("");
		StringBuilder outAnsRes = new StringBuilder("");	
		List<EntityAnswersText> answers;
		long id = (long)Integer.parseInt(questionID);
		EntityQuestionAttributes question = em.find(EntityQuestionAttributes.class, id);
		
				
		if(question != null){			
			if(ifAllowed(question)) {			
				answers = question.getQuestionAnswersList();
				String imageBase64Text=null;
				String fileLocation;
				
				////
				if((fileLocation = question.getFileLocationLink()) != null && question.getFileLocationLink().length() > 25 
						&& !question.getMetaCategory().equals(TestProcessor.MC_PROGRAMMING)){
					imageBase64Text = encodeImage(NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES  + fileLocation);
					outArray[1] = "data:image/png;base64," + imageBase64Text; // TO DO delete!!! hard code -- data:image/png;base64,
				}else{
					outArray[1] = null;
				}
				////
				
			switch(actionKey){
				case ACTION_GET_ARRAY: 
					outRes.append(question.getQuestionId().getQuestionText()).append(DELIMITER); // text of question or Description to picture or code									
					outRes.append(question.getCorrectAnswer()).append(DELIMITER); // correct answer char 
					outRes.append(question.getNumberOfResponsesInThePicture()).append(DELIMITER); // number of answers chars on image
					outRes.append(question.getMetaCategory());
					break;
					
				case ACTION_GET_FULL_ARRAY: 
					outRes.append(question.getQuestionId().getQuestionText()).append(DELIMITER);// text of question
					outRes.append(question.getDescription()).append(DELIMITER);  	// text of  Description 	
					outRes.append(question.getMetaCategory()).append(DELIMITER); // meta category 
					outRes.append(question.getCategory1()).append(DELIMITER); // language of sintax code in question
					outRes.append(question.getCategory2()).append(DELIMITER);// category of question
					outRes.append(question.getId()).append(DELIMITER);// static information
					outRes.append(question.getCorrectAnswer()).append(DELIMITER); // correct answer char 
					outRes.append(question.getNumberOfResponsesInThePicture()).append(DELIMITER);// number of answers chars on image					
					outRes.append(question.getLevelOfDifficulty());// level of difficulty for question
					break;
					
				default:System.out.println(" default switch");
			}		
			
			if(answers != null){
				for(EntityAnswersText tAn :answers) {
					outAnsRes.append(tAn.getAnswerText()).append(DELIMITER);	// answers on text if exist!!!
				}
				outArray[3] = outAnsRes.toString();
			}	
			outArray[0] = outRes.toString();// question attributes in text			
			outArray[2] = NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES + fileLocation;// that static parameter for one operation!. when question is update from admin panel !!!
		}else{
			outArray[1] = " "; // TO DO delete!!! hard code -- data:image/png;base64,
			outArray[0] = "wrong request q.Id-"+questionID;// out text stub for tests 
		}		
		}
		return outArray ;// return to client 
	}
	
	abstract protected boolean ifAllowed(EntityQuestionAttributes question);
	
	protected String encodeImage(String imageLink) {	//method getting jpg file and converting to base64 for sending to FES 		
		String res = null;
		byte[] bytes = null;
		FileInputStream file;
		try {
			file = new FileInputStream(imageLink);
			bytes = new byte[file.available()];
			file.read(bytes);
			file.close();
			res = Base64.getEncoder().encodeToString(bytes);
		} catch (FileNotFoundException e) {	} 
		catch (IOException e) {
			System.out.println("file not found");//-------------------------------------------------------------------------sysout	
		} 
		catch (NullPointerException e) {
			System.out.println("Null Pointer Exception");//-----------------------------------------------------------------sysout	
		}
		return res;
	}
	
	@Override
	public List<String> getCategories1ByMetaCategory(String metaCategory) {		
		String query = "Select DISTINCT q.category1 FROM EntityQuestionAttributes q WHERE c.metaCategory='"
				+ metaCategory + "'" + getLimitsForQueryWithAND() + " ORDER BY q.category1";
		return getQuery(query);
		
	}

}
