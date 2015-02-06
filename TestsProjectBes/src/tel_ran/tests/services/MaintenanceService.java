package tel_ran.tests.services;

import javax.persistence.*;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.services.interfaces.IMaintenanceService;

public class MaintenanceService extends TestsPersistence implements IMaintenanceService {
	
	@Transactional(readOnly=false)
	public void addQuestion(){
		//test commit
		QuestionEntity question=new QuestionEntity();
		em.persist(question);
	}
	public int getQuestionsCount(){
		Query query=em.createQuery("select count(q) from QuestionEntity q");
		long count=(Long) query.getSingleResult();
		return (int)count;
	}
	/*............................
	 *
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */

}
