package tel_ran.tests.services;

import java.util.List;

import javax.persistence.Query;

import tel_ran.tests.services.interfaces.IPersonalActionsService;

public class PersonalActionsService extends TestsPersistence implements IPersonalActionsService {
	
	@Override
	public List<String> getCategoriesList() {
	String query = "Select DISTINCT q.category FROM EntityQuestion q ORDER BY q.category";
	Query q = em.createQuery(query);
	List<String> allCategories = q.getResultList();
		
	return allCategories;
	}
	
	
	@Override
	public List<String> getComplexityLevelList() {
		String query = "Select DISTINCT q.level FROM EntityQuestion q ORDER BY q.level";
		Query q = em.createQuery(query);
		List<String> allLevels = q.getResultList();
		
		return allLevels;
	}

	@Override
	public String getMaxCategoryLevelQuestions(String catName,
			String complexityLevel) {
		String query = "SELECT q FROM EntityQuestion q WHERE q.category=?1 AND q.level=?2";
		Query q = em.createQuery(query);
		q.setParameter(1, catName);
		q.setParameter(2, Integer.parseInt(complexityLevel));
		List<EntityQuestion> qlist = q.getResultList();
		String res = String.valueOf(qlist.size());
		return res;
	}
	
}
