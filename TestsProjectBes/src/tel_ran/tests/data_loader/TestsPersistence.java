package tel_ran.tests.data_loader;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public abstract class TestsPersistence {
	
	static protected final int ADMIN_C_ID = 1;
	
	@PersistenceContext(unitName="springHibernate",
	type=PersistenceContextType.TRANSACTION)
	
	public EntityManager em;
	

		
}
