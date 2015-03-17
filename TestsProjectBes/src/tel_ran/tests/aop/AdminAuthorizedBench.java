package tel_ran.tests.aop;
import org.aspectj.lang.ProceedingJoinPoint;

public class AdminAuthorizedBench {
	public Object correctAdminAuthorized(ProceedingJoinPoint point) throws Throwable {		
		Object[]arguments = point.getArgs();
		return point.proceed(arguments);
		/*Object newObject = new Object();
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
		return outObject;*/
	}
}



