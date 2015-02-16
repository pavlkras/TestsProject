package tel_ran.tests.aop;



import org.aspectj.lang.ProceedingJoinPoint;

public class CompanyAuthorizedBench {
 public Object correctCompanyAuthorized(ProceedingJoinPoint point) throws Throwable{
	 Object[] arg = point.getArgs();
	 return point.proceed(arg);
	 
 }
}
