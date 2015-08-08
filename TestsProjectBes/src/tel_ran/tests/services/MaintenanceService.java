package tel_ran.tests.services;



import tel_ran.tests.entitys.EntityCompany;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.services.interfaces.IMaintenanceService;

/**
 * Main Class for Administration services
 * 
 */
public class MaintenanceService extends CommonAdminServices implements IMaintenanceService {	
	//
//	private static int NUMBERofRESPONSESinThePICTURE = 4;// number of responses in the picture, for text question`s default = 4

		
	///--------------- INNER PROTECTED METHODS -------------------------------- ///
		

	@Override
	protected EntityCompany renewCompany() {
		return null;
	}
	
	@Override
	protected boolean ifAllowed(EntityQuestionAttributes eqa) {
		return true;
	}
		
	@Override
	protected EntityCompany getCompany() {
		return null;
	}
		
	
	@Override
	protected String getLimitsForQuery() {
		return null;
	}

	////-------------- Reading from file and Adding Questions into DB Case ----------// BEGIN  //
	//  //  --------------------------------------------   TO DO factory method for this case !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//
//	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
//	public boolean FillDataBaseFromTextResource(List<String> inputTextFromFile){
//		// !!!!!!!!!!! worked only in Mozila and Hrome !!!!!!!!!!!
//		/*
//		 *  sample for text in file witch questions (THIS ONLY ONE LINE!!!)
//		 * 
//		 * questionText----description----fileLocationLink----
//		 * metaCategory----category----levelOfDifficulty----
//		 * answer1----answer2----answer3----answer4----
//		 * correctAnswerChar----questionIndexNumber----languageName
//		 * 
//		 */
//		boolean flagAction = false;
//		String fileLocationLink = null;
//		String questionText = null;
//		String category = null;
//		int levelOfDifficulty = 1;
//		List<String> answers = null;
//		String correctAnswer = null;
//		int questionNumber = 0;
//		String languageName = null;
//		String metaCategory = null;
//		String description = null; 
//		String codeText = null;
//		//
//		try{			
//			for(String line: inputTextFromFile){ 			
//				String[] question_Parts = line.split(DELIMITER); //delimiter for text, from interface IMaintenanceService
//				//
//				if(question_Parts.length > 6){		
//					questionText = question_Parts[0];
//					fileLocationLink = question_Parts[1];			
//					category = question_Parts[2];
//					levelOfDifficulty = Integer.parseInt(question_Parts[3]);
//					//
//					answers = new ArrayList<String>();									
//					answers.add(question_Parts[4]);	
//					answers.add(question_Parts[5]);
//					answers.add(question_Parts[6]);
//					answers.add(question_Parts[7]);				
//					//
//					correctAnswer = question_Parts[8];
//					questionNumber = 0;	
//					languageName = null;
//					metaCategory = null;
//					description = null; 
//					if(question_Parts[8] != null && question_Parts[8].length() > 3){
//						codeText = question_Parts[8];
//					}
//				}else{
//					//When question exist method added only new attributes for this question
//					//else if question not exist method added a new question full
//					questionNumber = Integer.parseInt(question_Parts[5]);	
//					questionText = question_Parts[0];
//					fileLocationLink = question_Parts[1];			
//					category = question_Parts[2];
//					levelOfDifficulty = Integer.parseInt(question_Parts[3]);
//					correctAnswer = question_Parts[4];
//					languageName = null;
//					metaCategory = null;// TO DO !!!!! create conections for field witch txt file
//					description = null; 
//					if(question_Parts[8] != null && question_Parts[8].length() > 3){
//						codeText = question_Parts[8];
//					}
//				}
//				//   -------------- !!! TO DO get to user question.sql file for fill users database !!!--------  ------------------  -----  //NUMBERofRESPONSESinThePICTURE ----- default = 4  
//				/*(questionText, fileLocationLink, metaCategory, category, levelOfDifficulty, answers, correctAnswer, questionNumber, numberOfResponsesInThePicture, description, codeText, languageName*/
//				flagAction = CreateNewQuestion(questionText, fileLocationLink, metaCategory, category, levelOfDifficulty, answers, correctAnswer, questionNumber, NUMBERofRESPONSESinThePICTURE, description ,  codeText, languageName );
//			}			
//		}catch(Exception e){
//			//e.printStackTrace();
//			System.out.println("catch from adding from file method BES");// ----- -------------- sysout
//		}
//		//
//		return flagAction;
//	}
	////-------------- Reading from file and Adding Questions into DB Case ----------// END  //
	
	
	////-------------- internal method for filling in the form update issue ----------// BEGIN  //
	
	
	////

	////-------------- internal method for filling in the form update issue ----------// END  //	
	//// ------------- Build Data end ---
	
	
	////-------------- Builder of page witch categories check box ----------// BEGIN  //	
//	@Override	
//	public List<String> getAllCategories1FromDataBase() {
//		String query = "Select DISTINCT q.category1 FROM EntityQuestionAttributes q ORDER BY q.category1";
//		return getQuery(query);
//	}
//
//	@Override
//	public List<String> getAllCategories2FromDataBase() {
//		String query = "Select DISTINCT q.category2 FROM EntityQuestionAttributes q ORDER BY q.category2";
//		return getQuery(query);
//	}
//	@Override
//	public List<String> getAllMetaCategoriesFromDataBase() {
//		String query = "Select DISTINCT q.metaCategory FROM EntityQuestionAttributes q ORDER BY q.metaCategory";
//		return getQuery(query);
//	}	
	////-------------- Builder of page witch categories check box ----------// END  //
	

			
}

//// ----- END Code -----