package tel_ran.tests.services;

import java.sql.Date;

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
		String questions = personTest.getQuestion();

		String[] outResult = {peRson+"", pass, questions};
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
			personTest.setTestDate(new Date(timeStartTest));	
			int amountOfCorrectAnswers = personTest.getCorrectAnswers().length;
			personTest.setAmountOfCorrectAnswers(amountOfCorrectAnswers );
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
		System.out.println("personAnswers-"+personAnswers);
		boolean resAction = false;
		try{
			long testID = (long)Integer.parseInt(testId);		
			EntityTest personTest = em.find(EntityTest.class, testID);		
			personTest.setPersonAnswers(personAnswers.toCharArray());
			personTest.setPictures(imagesLinks);		
			personTest.setTestDate(new Date(timeEndTest));				
			em.persist(personTest);
			resAction = true;
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("catch save end test");
		}
		return resAction;
	}
	////------- Control mode Test for Person case ----------------// END //
}
