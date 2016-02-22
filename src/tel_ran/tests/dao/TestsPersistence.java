package tel_ran.tests.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import tel_ran.tests.entitys.Company;
import tel_ran.tests.services.fields.Role;

public abstract class TestsPersistence implements IData {
	
	static protected final int ADMIN_C_ID = 1;
	
	@PersistenceContext(unitName="springHibernate",
	type=PersistenceContextType.TRANSACTION)
	
	public EntityManager em;
	

	public Company getCompanyById(int id, Role role) {
				
		if(role.equals(Role.ADMINISTRATOR)) id = ADMIN_C_ID;
		if(id<=0) return null;
		return em.find(Company.class, id);
		
		
	}
		
}
