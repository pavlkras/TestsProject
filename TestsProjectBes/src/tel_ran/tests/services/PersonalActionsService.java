package tel_ran.tests.services;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
				personTest.setCounterPicturesOfTheTest(1);// start counter of pictures
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
	public boolean SaveEndPersonTestResult(String testId, String personAnswers,	String imagesLinks, long timeEndTest) {		
		boolean resAction = false;
		long testID = (long)Integer.parseInt(testId);	
		EntityTest personTest = em.find(EntityTest.class, testID);
		////
		if(testId.length() >= 1 && personAnswers == null && imagesLinks.length() > 10 && timeEndTest == 0 && personTest.getCounterPicturesOfTheTest() <= 5){// condition IN TEST saving person pictures
			try{							
				String links = personTest.getPictures();
				String companyId = personTest.getEntityCompany().getC_Name();
				links += getLinkForImages(imagesLinks, companyId , testId) + ","; // saving pictures and save to db link to picture (delimlter = , )
				personTest.setPictures(links);
				personTest.setCounterPicturesOfTheTest(personTest.getCounterPicturesOfTheTest()+1);
				em.persist(personTest);
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("BES catch Save pictures");//-------------------------------------------------sysout
			}
			////
		}else if(imagesLinks == null && personTest.getCounterPicturesOfTheTest() > 5){// condition end of test , preparing to save parameters
			////
			try{
				char[] persAnswArray = personAnswers.toCharArray();
				personTest.setPersonAnswers(persAnswArray );
				if(personTest.getEndTestDate() == 0){
					personTest.setEndTestDate(timeEndTest);
				}
				personTest.setPersonAnswers(personAnswers.toCharArray());	
				personTest.setAmountOfCorrectAnswers(AmountOfAnswers(personTest));
				//
				em.persist(personTest);
				resAction = true;			
			}catch(Exception e){			
				if(testId != null && personAnswers == null && imagesLinks == null && timeEndTest != 0L){
					personTest.setEndTestDate(timeEndTest);
					em.persist(personTest);
					System.out.println("BES test id-"+testID+"  time end-"+timeEndTest);//-------------------------------------------------sysout
				}
				//e.printStackTrace();
				System.out.println("BES catch save end test");//---------------------------------------------------------------------------sysout
			}
		}// end first if/else
		return resAction;
	}
	////-------  save images  ---------------// Begin //
	private String getLinkForImages(String imagesLinks, String companyId,String testId) {
		String outLinkText = "";/// stub empty images links
		String workingDir = System.getProperty("user.dir");
		try {
			long idOfTest = (long)Integer.parseInt(testId);
			EntityTest testRes = em.find(EntityTest.class, idOfTest );	
			int picNum = testRes.getCounterPicturesOfTheTest();
			String testName = testRes.getTestName();
			////
			Files.createDirectories(Paths.get(NAME_FOLDER_FOR_SAVENG_TESTS_PICTURES + File.separator + companyId + File.separator + testName));// creating a new directiry for saving person test pictures  
			////
			BufferedWriter writer =  new BufferedWriter ( new FileWriter(workingDir 
					+ File.separator + NAME_FOLDER_FOR_SAVENG_TESTS_PICTURES 
					+ File.separator + companyId 
					+ File.separator + testName + "\\pic_" + picNum + ".txt"));
			writer.write(imagesLinks);			 
			writer.close();
			////				
			outLinkText = NAME_FOLDER_FOR_SAVENG_TESTS_PICTURES + File.separator + companyId + File.separator + testName + "\\pic_" + picNum + ".txt";			
		} catch (IOException e) {
			e.printStackTrace();  
		}		
		System.out.println("outLinkText="+outLinkText);//---------------------------------------------------------------------------sysout
		return outLinkText;		
	}
	////-------  save images ----------------// END //	
	////  --------- auxiliary internal method
	private int AmountOfAnswers(EntityTest personTest) {               // ------------------------- TO DO sampfing for true amount for answers
		char[] corrAns = personTest.getCorrectAnswers();
		char[] persAns = personTest.getPersonAnswers();
		//String persCodeAns = personTest.getResultTestingCodeFromPerson();
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
	@Override
	public boolean TestCodeQuestionCase(String codeText, String questionID, long idOfTest) {
		boolean result = false;
		EntityTest testRes = em.find(EntityTest.class, idOfTest);	

		//   --------------------  TO DO methods and actions // TO DO !!!!!!!!!  for Intelege case
		if(codeText != null){
			testRes.setResultTestingCodeFromPerson(questionID + "-true");
			result = true;
		}else{
			testRes.setResultTestingCodeFromPerson(questionID + "-false");
		} 
		return result;
	}
	////-------  Test Code From Users and Persons Case  ----------------// END //	
}

