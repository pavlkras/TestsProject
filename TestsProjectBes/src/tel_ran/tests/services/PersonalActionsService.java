package tel_ran.tests.services;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.services.interfaces.IPersonalActionsService;

public class PersonalActionsService extends TestsPersistence implements	IPersonalActionsService {

	private  String BASE_DIRECTORY_FOR_SAVING_PICTURES_TO_FILE = "baseTests";

	////------- Control mode Test for Person case ----------------// BEGIN //
	@Override
	public String[] GetTestForPerson(String testId) {	// creation test for person
		long testID = (long)Integer.parseInt(testId);		
		EntityTest personTest = em.find(EntityTest.class, testID);		
		int peRson = personTest.getEntityPerson().getPersonId();
		String pass = personTest.getPassword();	
		String idQuestionsForTheTest = personTest.getIdQuestionsForCreationTest();
		String[] outResult = {peRson+"", pass, idQuestionsForTheTest};
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
			personTest.setStartTestDate(timeStartTest);
			//
			em.persist(personTest);
			resAction = true;
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("catch save start test");
		}
		return resAction;
	}
	//// ------------------- save ending test parameters
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean SaveEndPersonTestResult(String testId, String personAnswers,	String imagesLinks, long timeEndTest) {
		String[] res = imagesLinks.split("@end_of_link@");  // here Links OF PersonIMAGE in array string as 1 string = 1 img link base64!!!
		for(int i=0;i<res.length;i++){
			res[i]=res[i].replaceFirst(",", "");
		}
		////
		boolean resAction = false;
		try{
			long testID = (long)Integer.parseInt(testId);		
			EntityTest personTest = em.find(EntityTest.class, testID);		
			char[] persAnswArray = personAnswers.toCharArray();
			personTest.setPersonAnswers(persAnswArray );
			String companyId = personTest.getEntityCompany().getC_Name();
			String links = getLinksForImages(res, companyId , testId); 
			personTest.setPictures(links);	

			personTest.setEndTestDate(timeEndTest);	
			personTest.setPersonAnswers(personAnswers.toCharArray());	
			personTest.setAmountOfCorrectAnswers(AmountOfAnswers(personTest));
			//
			em.persist(personTest);
			resAction = true;			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("catch save end test");
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
	private String getLinksForImages(String [] imagesLinksInBase64Text, String companyID, String testID){
		String outLinkText = "";/// stub empty images links
		String fileDirectory = BASE_DIRECTORY_FOR_SAVING_PICTURES_TO_FILE;	
		String workingDir = System.getProperty("user.dir").replaceAll("\\\\", "/");
		try {			
			//Files.createDirectories(Paths.get(fileDirectory +"/comp_" + companyID + "/test_" + testID));	
			Files.createDirectories(Paths.get(fileDirectory +"\\comp_" + companyID + "\\test_" + testID));
			for(int picNum=0; picNum < imagesLinksInBase64Text.length; picNum++){	
				//BufferedWriter writer =  new BufferedWriter ( new FileWriter(workingDir + "/" + fileDirectory +"/comp_" + companyID + "/test_" + testID + "/pic_" + picNum + ".txt"));
				BufferedWriter writer =  new BufferedWriter ( new FileWriter(workingDir + "\\" + fileDirectory +"\\comp_" + companyID + "\\test_" + testID + "\\pic_" + picNum + ".txt"));
				writer.write(imagesLinksInBase64Text[picNum]);			 
				writer.close();
				//outLinkText += fileDirectory +"/comp_" + companyID + "/test_" + testID + "/pic_" + picNum + ".txt" + " , ";
				outLinkText += fileDirectory +"\\comp_" + companyID + "\\test_" + testID + "\\pic_" + picNum + ".txt" + ",";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		System.out.println("outLinkText-"+outLinkText);	//------------------------------sysout	
		return outLinkText;
	}
	////-------  save images ----------------// END //	
}

