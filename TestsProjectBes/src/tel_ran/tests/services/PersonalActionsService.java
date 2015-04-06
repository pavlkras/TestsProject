package tel_ran.tests.services;
import java.util.Base64;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.services.interfaces.IPersonalActionsService;

public class PersonalActionsService extends TestsPersistence implements	IPersonalActionsService {

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
		System.out.println("length of array witch imglinks-" + res.length);	//---------------------------------------------------------------------sysout
		String links = getLinkImageFromBase64(res); //something.png,something.png
		////
		boolean resAction = false;
		try{
			long testID = (long)Integer.parseInt(testId);		
			EntityTest personTest = em.find(EntityTest.class, testID);		
			char[] persAnswArray = personAnswers.toCharArray();
			personTest.setPersonAnswers(persAnswArray );
			personTest.setPictures(links);		
			personTest.setEndTestDate(timeEndTest);	
			personTest.setPersonAnswers(personAnswers.toCharArray());	
			personTest.setAmountOfCorrectAnswers(AmountOfAnswers(personTest));
			//
			em.persist(personTest);
			resAction = true;			
		}catch(Exception e){
			//e.printStackTrace();
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

	////------- decoding and save images  ---------------// Begin //
	private String getLinkImageFromBase64(String [] res){
		String outLinkText = " , , , , , ";// stub empty images links
		
		/*if(res!=null){
			String [] decodedImages = decodeToBase64(res);
			System.out.println("decodedImages length-"+decodedImages.length);
		}*/
		
		return outLinkText;
	}
	////
	private String [] decodeToBase64(String[] res){
		String [] outRes = new String[res.length];
		for(int i=0; i<res.length; i++){
			outRes[i] = Base64.getDecoder().decode(res[i]).toString();  //decoding 
			System.out.println("outRes[i]=="+outRes[i]);
		}
		return outRes;
	}
	////------- decoding and save images ----------------// END //
}
