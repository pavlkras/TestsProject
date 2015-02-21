package tel_ran.tests.aop;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import tel_ran.tests.services.MaintenanceService;

public class AdminAuthorizedBench {
	@SuppressWarnings("unchecked")
	public Object correctAdminAuthorized(ProceedingJoinPoint point) {		
		Object[]arguments = point.getArgs();
		Object newObject = new Object();
		Object outObject="";

		if(arguments[0] instanceof Boolean && arguments.length==1){
			try {
				newObject = point.proceed(arguments);
			} catch (Throwable e) {
				System.out.println("User is not Autorized");
			}	
		}
		try {
			boolean flAuthorization = MaintenanceService.isAuthorized();
			if(flAuthorization){
				newObject = point.proceed(arguments);				
			}			
			if(newObject instanceof Boolean){
				outObject = (boolean) newObject;
			}else if(newObject instanceof String){
				outObject = (String) newObject;
			}else if(newObject instanceof List){
				outObject = (List<String>) newObject;
			}else if(newObject instanceof List){
				outObject = (List<Long>) newObject;
			}		
		} catch (Throwable e) {				
			System.out.println("User is not Autorized");
		}		
		return outObject;
	}
}



