package tel_ran.tests.aop;
import org.aspectj.lang.ProceedingJoinPoint;

import tel_ran.tests.services.MaintenanceService;


public class AuthorizedBench {
	public Object correctAuthorized(ProceedingJoinPoint point) {
	    Object[]args=point.getArgs();
	    Object result = new Object();
        boolean res = false;
		if(args[0] instanceof Boolean && args.length==1){
			try {
				    result=point.proceed(args);
					}
              catch (Throwable e) {    
				System.out.println("jmnmn1");
			}
		}else{
		    res = MaintenanceService.isAuthorized();
			try {
			if(res){
				result=point.proceed(args);
				} 
          } catch (Throwable e) {    
			System.out.println("jmnmn3");
		}
				
		}
      return result;
	}

}



