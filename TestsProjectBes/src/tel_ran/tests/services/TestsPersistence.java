package tel_ran.tests.services;
import javax.persistence.*;
public class TestsPersistence {
@PersistenceContext(unitName="springHibernate",
type=PersistenceContextType.EXTENDED)
EntityManager em;
}
