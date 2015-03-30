package tel_ran.tests.services;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.services.interfaces.IPersonalActionsService;

public class PersonalActionsService extends TestsPersistence implements	IPersonalActionsService {

	////------- Control mode Test for Person case ----------------// BEGIN //
	@Override
	public String[] GetTestForPerson(String testId) {	
		long testID = (long)Integer.parseInt(testId);		
		EntityTest personTest = em.find(EntityTest.class, testID);		
		int peRson = personTest.getEntityPerson().getPersonId();
		String pass = personTest.getPassword();	
		String idQuestionsForTheTest = personTest.getIdQuestionsForCreationTest();
		String[] outResult = {peRson+"", pass, idQuestionsForTheTest};
		return outResult;
	}
	////	
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
	////
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean SaveEndPersonTestResult(String testId, String personAnswers,	String imagesLinks, long timeEndTest) {

		boolean resAction = false;
		try{
			long testID = (long)Integer.parseInt(testId);		
			EntityTest personTest = em.find(EntityTest.class, testID);		
			char[] persAnswArray = personAnswers.toCharArray();
			personTest.setPersonAnswers(persAnswArray );
			personTest.setPictures(imagesLinks);		
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
	////
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
}
