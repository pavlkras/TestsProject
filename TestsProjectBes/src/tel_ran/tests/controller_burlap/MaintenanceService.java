package tel_ran.tests.controller_burlap;


import tel_ran.tests.entitys.Company;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.services.interfaces.IMaintenanceService;

/**
 * Main Class for Administration services
 * 
 */
public class MaintenanceService extends CommonAdminServices implements IMaintenanceService {	
	//
//	private static int NUMBERofRESPONSESinThePICTURE = 4;// number of responses in the picture, for text question`s default = 4

		
	///--------------- INNER PROTECTED METHODS -------------------------------- ///
		

	@Override
	protected Company renewCompany() {
		return null;
	}
	
	@Override
	protected boolean ifAllowed(EntityQuestionAttributes eqa) {
		return true;
	}
		
	@Override
	protected Company getCompany() {
		return null;
	}
		
	
	@Override
	protected String getLimitsForQuery() {
		return null;
	}
	


	

			
}

//// ----- END Code -----