package tel_ran.tests.dao;

import tel_ran.tests.entitys.Company;
import tel_ran.tests.services.fields.Role;

public interface IData {
	
	Company getCompanyById(int id, Role role);
}
