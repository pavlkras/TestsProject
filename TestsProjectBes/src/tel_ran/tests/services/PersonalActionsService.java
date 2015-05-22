package tel_ran.tests.services;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.services.interfaces.IPersonalActionsService;

public class PersonalActionsService extends TestsPersistence implements	IPersonalActionsService {	
	////------- Control mode Test for Person case ----------------// BEGIN //
	@Override
	public String[] GetTestForPerson(String testId) {	// creation test for person
		String[] outResult = null;	
		////
		EntityTest personTest = null;
		if(testId != null && testId != ""){
			long testID = (long)Integer.parseInt(testId);		
			personTest = em.find(EntityTest.class, testID);	
		}
		////
		if(personTest != null){	
			outResult = new String[5];
			outResult[0] = personTest.getEntityPerson().getPersonId() + "";
			outResult[1] = personTest.getPassword();
			outResult[2] = personTest.getIdQuestionsForCreationTest();
			outResult[3] = personTest.getStartTestDate() + "";
			outResult[4] = personTest.getEndTestDate() + "";	
		}	
		////	
		return outResult;
	}
	//// ------------------- save starting test	parameters
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean SaveStartPersonTestParam(String testId, String correctAnswers, long timeStartTest) {
		boolean resAction = false;
		try{
			long testID = (long)Integer.parseInt(testId);		
			EntityTest personTest = em.find(EntityTest.class, testID);		
			personTest.setCorrectAnswers(correctAnswers.toCharArray());	// TO DO --------------------------
			if(personTest.getStartTestDate() == 0){//   if this first time!!!
				personTest.setStartTestDate(timeStartTest);					
				em.persist(personTest);
				resAction = true;
			}
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("catch save start test");//-------------------------------------------------------------------------------------sysout
		}
		return resAction;
	}
	//// ------------------- save ending test parameters
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean SaveEndPersonTestResult(String testId, String personAnswers,	String personAnswersCode, String imagesOfPerson, String screenShoots, long timeEndTest) {		
		boolean resAction = false;
		long testID = (long)Integer.parseInt(testId);
		String links[] = {};

		EntityTest personTest = em.find(EntityTest.class, testID);		
		try{
			String companyId = personTest.getEntityCompany().getC_Name();
			links = getLinkForImages(imagesOfPerson, screenShoots, companyId , testId); // saving pictures and save to db link to picture 
			personTest.setPictures(links[0]);
			personTest.setScreensOfPerson(links[1]);				
			char[] persAnswArray = personAnswers.toCharArray();
			personTest.setPersonAnswers(persAnswArray);
			if(personTest.getEndTestDate() == 0){
				personTest.setEndTestDate(timeEndTest);
			}
			int amountCorrectAnswers = 0;
			int amountCorrectAnswersCode = 0;
			if(personAnswers != null){
				amountCorrectAnswers = AmountOfAnswers(personTest);
			}
			if(personAnswersCode != null){
				amountCorrectAnswersCode = AmountOfAnswersCode(personAnswersCode, testId);
			}
			personTest.setAmountOfCorrectAnswers(amountCorrectAnswers + amountCorrectAnswersCode);
			em.persist(personTest);
			resAction = true;			
		}catch(Exception e){	
			if(testId != null && personAnswers == null && imagesOfPerson == null && timeEndTest != 0L){
				personTest.setEndTestDate(timeEndTest);
				em.persist(personTest);
				System.out.println("BES test id-"+testID+"  time end-"+timeEndTest);//-------------------------------------------------sysout
			}
			//e.printStackTrace();
			System.out.println("BES catch save end test");//---------------------------------------------------------------------------sysout
		}

		return resAction;
	}
	////-------  save images  ---------------// Begin //
	private String[] getLinkForImages(String imagesOfPerson, String screenShoots, String companyId, String testId) {
		String[] res_pic = imagesOfPerson.split(IMAGE_DELIMITER);
		String[] res_screen = screenShoots.split(IMAGE_DELIMITER);
		String tempResScreen = "";
		String tempResPictures = "";
		String[] outLinkText = {};/// stub empty images links
		String workingDir = System.getProperty("user.dir");
		try {
			EntityTest testRes = em.find(EntityTest.class, (long)Integer.parseInt(testId) );
			String testName = testRes.getTestName();
			////
			String s_path = NAME_FOLDER_FOR_SAVENG_TESTS_FILES + File.separator + companyId + File.separator + testName;
			Files.createDirectories(Paths.get(s_path));// creating a new directiry for saving person test pictures  
			////
			for(int i=0;i<res_pic.length;i++){
				BufferedWriter writer =  new BufferedWriter ( new FileWriter(workingDir + s_path + "\\picture_" + i + ".txt"));
				writer.write(res_pic[i]);			 
				writer.close();
				////		
				writer =  new BufferedWriter ( new FileWriter(workingDir + s_path + "\\screen_" + i + ".txt"));
				writer.write(res_screen[i]);			 
				writer.close();
				////
				tempResPictures += s_path + "\\picture_" + i + ".txt" + IMAGE_DELIMITER;	
				tempResScreen += s_path + "\\screen_"  + i + ".txt" + IMAGE_DELIMITER;
			}
			outLinkText[0] = tempResPictures;
			outLinkText[1] = tempResScreen;

		} catch (IOException e) {
			e.printStackTrace();  
		}		
		return outLinkText;		
	}
	////-------  save images ----------------// END //	
	//       auxiliary internal method
	private int AmountOfAnswers(EntityTest personTest) {               // ------------------------- TO DO sampfing for true amount for answers
		char[] corrAns = personTest.getCorrectAnswers();
		char[] persAns = personTest.getPersonAnswers();
		int amountTrueAnswers=0;
		for(int i=0;i<corrAns.length;i++){
			if(corrAns[i] == persAns[i]){
				amountTrueAnswers++;
			}
		}
		return amountTrueAnswers;
	}
	////------- Control mode Test for Person case ----------------// END //
	////-------  Test Code From Users and Persons Case  ----------------// BEGIN //	
	private int AmountOfAnswersCode(String personAnswersCode, String testId) {
		int result = 0;
		long testID = (long)Integer.parseInt(testId);
		String[] str = personAnswersCode.split(IMAGE_DELIMITER);
		EntityTest testRes = em.find(EntityTest.class, testID);	
		StringBuffer pathToAnswersCode = new StringBuffer();
		StringBuffer resultAnswersCode = new StringBuffer();
		for(int i=0; i<str.length; i=i+2){
			String pathCode = getPathToCodeAnswer(str[i], str[i+1], testID);
			long questionID = (long)Integer.parseInt(str[i+1]);
			EntityQuestionAttributes question = em.find(EntityQuestionAttributes.class, questionID);
			String pathZip = question.getFileLocationLink();            //String pathZip = question.getFileLocationZip();
			boolean answer = getResultOfExecutionCode(pathZip, pathCode);     
			if(i < str.length-2){
				pathToAnswersCode.append(pathCode + ",");				
				resultAnswersCode.append(String.valueOf(answer) + ",");
			}
			else{
				pathToAnswersCode.append(pathCode);
				resultAnswersCode.append(String.valueOf(answer));
			}		
			if(answer){
				result++;
			}	
		}
		testRes.setTestCodeFromPerson(pathToAnswersCode.toString());
		testRes.setResultTestingCodeFromPerson(resultAnswersCode.toString());       
		return result;
	}
	////
	private String getPathToCodeAnswer(String codeText, String questionID, long idOfTest) {
		String res = "";
		String workingDir = System.getProperty("user.dir");
		try {
			EntityTest testRes = em.find(EntityTest.class, idOfTest);	
			String testName = testRes.getTestName();
			String companyName = testRes.getEntityCompany().getC_Name(); 
			Files.createDirectories(Paths.get(NAME_FOLDER_FOR_SAVENG_TESTS_FILES + File.separator + companyName + File.separator + testName + File.separator + "codeAnswers")); 
			BufferedWriter writer =  new BufferedWriter ( new FileWriter(workingDir 
					+ File.separator + NAME_FOLDER_FOR_SAVENG_TESTS_FILES 
					+ File.separator + companyName 
					+ File.separator + testName 
					+ File.separator + "codeAnswers" +
					"\\question_" + questionID + ".txt"));
			writer.write(codeText);			 
			writer.close();
			////				
			res = NAME_FOLDER_FOR_SAVENG_TESTS_FILES + File.separator + companyName + File.separator + testName + File.separator + "codeAnswers" + "\\question_" + questionID + ".txt";			
		} catch (IOException e) {
			e.printStackTrace();  
		}		
		return res;
	}

	private boolean getResultOfExecutionCode(String pathZip, String pathCode) {  

		//start of Gradle job
		// TODO Auto-generated method stub

		return true;
	}	
	////-------  Test Code From Users and Persons Case  ----------------// END //	
}

