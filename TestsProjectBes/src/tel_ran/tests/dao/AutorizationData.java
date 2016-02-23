package tel_ran.tests.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import json_models.AutorizationModel;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.Company;
import tel_ran.tests.entitys.EntityUser;


public class AutorizationData extends TestsPersistence implements IDataLoader {

	@Override
	@Transactional
	public long checkUserLogIn(String email, String password) {
		String queryText = "Select c from EntityUser c where c.email=?1 and c.password=?2";		
		Query q = (Query) em.createQuery(queryText);
		q.setParameter(1, email);
		q.setParameter(2, password);
		
		EntityUser user = null;
		
		try {
			user = (EntityUser) q.getSingleResult();
		} catch(NoResultException e) {
			return -1;
		}
		if(user==null)
			return -1;	
		
		return user.getId();
	}
	
	@Override
	@Transactional
	public long checkCompanyLogIn(String name, String password) {
		String queryText = "Select ec from Company ec where ec.C_Name=?1 and ec.C_Password=?2";
		Query q = (Query) em.createQuery(queryText);
		q.setParameter(1, name);
		q.setParameter(2, password);
		
		Company company = null;
		
		try {
			company = (Company) q.getSingleResult();
		} catch(NoResultException e) {
			return -1;
		}
		
		return company.getId();
	}


	@Override
	@Transactional
	public boolean isAdmin(long id) {
		boolean result = false;
		EntityUser user = em.find(EntityUser.class, id);
		if(user!=null)
			result = user.isAdminAccess();
		return result;
	}

	@Override
	@Transactional(readOnly=false)
	public boolean checkUserEmail(String email) {
		boolean result = false;
		String queryText = "Select c from EntityUser c where c.email=?1";
		Query q = (Query) em.createQuery(queryText);
		q.setParameter(1, email);
				
		if(!q.getResultList().isEmpty()) {
			result = true;
		} 			
		return result;
	}

	@Override
	@Transactional
	public boolean checkCompanyName(String login) {
		boolean result = false;
		String queryText = "Select c from Company c where c.C_Name=?1";
		Query q = (Query) em.createQuery(queryText);
		q.setParameter(1, "%"+login+"%");				
		if(!q.getResultList().isEmpty()) {
			result = true;
		} 			
		return result;
	}
	
		
	@Override
	@Transactional
	public boolean checkRootUser() {		
		String queryText = "SELECT COUNT(eu) FROM EntityUser eu WHERE eu.id=1";
		long result = (long) em.createQuery(queryText).getSingleResult();
		if(result>0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public boolean userRegistration(String email, String password) {						
		return userRegistration(email, password, false);
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public boolean userRegistration(String email, String password, boolean isAdmin) {
		EntityUser user = new EntityUser();	
		user.setPassword(password);
		user.setEmail(email);
		user.setAdminAccess(isAdmin);
		em.persist(user);				
		return true;
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public boolean userRegistration(String email, String password,
			String firstName, String lastName, String nickName) {
		EntityUser user = new EntityUser();	
		user.setPassword(password);
		user.setEmail(email);
		if(firstName!=null && firstName.length()>0)	
			user.setFirstName(firstName);
		if(lastName!=null && lastName.length()>0)	
			user.setFirstName(lastName);
		if(nickName!=null && nickName.length()>0)	
			user.setFirstName(firstName);
		em.persist(user);				
		return true;
	}

	@Override
	@Transactional
	public void fillInfoAboutUser(AutorizationModel model, long id) {
		EntityUser user = em.find(EntityUser.class, id);
		model.setAddress(user.getAddress());		
		model.setBirthDate(user.getBirthdate());
		model.setFirstName(user.getFirstName());
		model.setLastName(user.getLastName());
		model.setNickName(user.getNickname());
		model.setPassport(user.getPassportNumber());		
	}

	@Override
	@Transactional
	public void fillInfoAboutCompany(AutorizationModel model, int id) {
		Company company = em.find(Company.class, id);
		model.setEmail(company.getC_email());
		model.setWebSite(company.getC_Site());
		model.setSpec(company.getC_Specialization());
		model.setEmployesNumber(company.getC_AmountEmployes());
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public boolean companyRegistration(String login, String password,
			String employesNumber, String webSite, String spec) {
		Company company = new Company();
		company.setC_Password(password);
		company.setC_Name(login);
		
		if(employesNumber!=null)
			company.setC_AmountEmployes(employesNumber);
		if(webSite!=null)
			company.setC_Site(webSite);
		if(spec!=null)
			company.setC_Specialization(spec);
	
		em.persist(company);				
		return true;
	}

	@Override
	public boolean checkFirstCompany() {
		Company ec = em.find(Company.class, 1);
		if(ec==null) return false;
		return true;
	}

	@Override
	public long findTestIdByPassword(String key) {

		try {
			long testId = (long) em.createQuery("Select t.id from Test t where t.password='" + key + "'").getSingleResult();
			System.out.println(testId);
			return testId;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	

}
