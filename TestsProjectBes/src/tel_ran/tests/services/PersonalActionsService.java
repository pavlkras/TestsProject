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
			System.out.println("BES get test - " + personTest);//-------------------------------------------------------------------------------------sysout
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
			personTest.setCorrectAnswers(correctAnswers.toCharArray());	
			if(personTest.getStartTestDate() == 0){// if this first time!!!
				personTest.setStartTestDate(timeStartTest);	
				System.out.println("saved time -"+ timeStartTest);//-------------------------------------------------------------------------------------sysout
				//
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
		////
		try{
			long testID = (long)Integer.parseInt(testId);		
			EntityTest personTest = em.find(EntityTest.class, testID);		
			char[] persAnswArray = personAnswers.toCharArray();
			personTest.setPersonAnswers(persAnswArray );
			String companyId = personTest.getEntityCompany().getC_Name();
			String links = getLinksForImages(imagesLinks, companyId , testId); 
			personTest.setPictures(links);
			if(personTest.getEndTestDate() == 0){
				personTest.setEndTestDate(timeEndTest);
			}
			personTest.setPersonAnswers(personAnswers.toCharArray());	
			personTest.setAmountOfCorrectAnswers(AmountOfAnswers(personTest));
			//
			em.persist(personTest);
			System.out.println("BES end person Test -- "+personTest);//-------------------------------------------------------------------------------------sysout
			resAction = true;			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("catch save end test");//-------------------------------------------------------------------------------------sysout
		}
		return resAction;
	}
	////  --------- auxiliary internal method
	private int AmountOfAnswers(EntityTest personTest) {
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

	////-------  save images  ---------------// Begin //
	private String getLinksForImages(String imagesLinksInBase64Text, String companyID, String testID){		
		String outLinkText = "";/// stub empty images links
		String workingDir = System.getProperty("user.dir");
		try {
			String[] tempPicturesArray = imagesLinksInBase64Text.split(IMAGE_DELIMITER);  // here Links OF PersonIMAGE in array string as 1 string = 1 img link base64!!!	
			////
			long idOfTest = (long)Integer.parseInt(testID);
			EntityTest testRes = em.find(EntityTest.class, idOfTest );				
			String testName = testRes.getTestName();
			////
			Files.createDirectories(Paths.get(NAME_FOLDER_FOR_SAVENG_TESTS_PICTURES + File.separator + companyID + File.separator + testName));// creating a new directiry for saving person test pictures  
			for(int picNum=0; picNum < tempPicturesArray.length; picNum++){	
				tempPicturesArray[picNum].replaceFirst(",", "");				
				BufferedWriter writer =  new BufferedWriter ( new FileWriter(workingDir + File.separator + NAME_FOLDER_FOR_SAVENG_TESTS_PICTURES + File.separator + companyID + File.separator + testName + "\\pic_" + picNum + ".txt"));
				writer.write(tempPicturesArray[picNum]);			 
				writer.close();
				////				
				outLinkText += NAME_FOLDER_FOR_SAVENG_TESTS_PICTURES + File.separator + companyID + File.separator + testName + "\\pic_" + picNum + ".txt" + ",";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return outLinkText;
	}
	////-------  save images ----------------// END //	
}

