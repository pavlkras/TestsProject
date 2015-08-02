package tel_ran.tests.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public abstract class TestsPersistence {
	
	@PersistenceContext(unitName="springHibernate",
	type=PersistenceContextType.TRANSACTION)
	
	public EntityManager em;
	

		
}
