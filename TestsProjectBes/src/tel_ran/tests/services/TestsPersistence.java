package tel_ran.tests.services;
import java.util.List;

import javax.persistence.*;
public class TestsPersistence {
@PersistenceContext(unitName="springHibernate",
type=PersistenceContextType.EXTENDED)
EntityManager em;


}
