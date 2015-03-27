package tel_ran.tests.services;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
public class TestsPersistence {
@PersistenceContext(unitName="springHibernate",
type=PersistenceContextType.EXTENDED)
EntityManager em;
}
