package tel_ran.tests.services;

import java.util.List;

import javax.persistence.Query;

import tel_ran.tests.services.interfaces.IPersonalActionsService;

public class PersonalActionsService extends TestsPersistence implements IPersonalActionsService {
	
	@Override
	public List<String> getCategoriesList() {
	String query = "Select DISTINCT q.category FROM MaintenanceQuestion q ORDER BY q.category";
	Query q = em.createQuery(query);
	List<String> allCategories = q.getResultList();
		
	return allCategories;
	}
	
	@Override
	public String getMaxCategoryQuestions(String catName) {
	String query = "SELECT q FROM MaintenanceQuestion q WHERE q.category=?1";
	Query q = em.createQuery(query);
	q.setParameter(1, catName);
	List<MaintenanceQuestion> qlist = q.getResultList();
	String res = String.valueOf(qlist.size());
	return res;
	}
	
	@Override
	public int testCreationService(String userId) {
	return 0;
	}
	
	@Override
	public boolean saveUserService(String[] args) {
	return false;
	}
	
	@Override
	public String[] loadUserservice(String userId) {
	return null;
	}
	
	@Override
	public int testCreationByCategory(String userId, String category, int level, int qAmount) {
	return 0;
	}
	
	@Override
	public String[] loadTestService(int id) {
	return null;
	}
	
	@Override
	public List<Integer> getLevelsList() {
	return null;
	}
	
	@Override
	public String loadXMLTest(int id) {
	return null;
	}
	
}
