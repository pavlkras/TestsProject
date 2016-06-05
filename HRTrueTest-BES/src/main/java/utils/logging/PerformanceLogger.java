package main.java.utils.logging;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestHeader;

import main.java.model.config.NamesAndFormats;

/**
 * @author Pavel
 *
 */
public class PerformanceLogger extends AbstractFileLogger {
	/* format: "Timestamp userID class method message */
	private static final String LOG_FORMAT = "%s\t%d\t%s\t%s\t%s%n";
	private static final String MESSAGE = "Executed in %f sec.";

	public PerformanceLogger(String fileName) {
		super(fileName);
	}

	@Override
	public Object makeLog(ProceedingJoinPoint pjp) throws Throwable {
		Long id = null;
		String clazz = pjp.getTarget().getClass().getSimpleName();
		String methodName = pjp.getSignature().getName();
		
		Object[] args = pjp.getArgs();
		MethodSignature methodSignature = (MethodSignature) pjp.getStaticPart().getSignature();
		Method method = methodSignature.getMethod();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		
		for (int i = 0; i < args.length; ++i){
			for (Annotation annotation : parameterAnnotations[i]){
				if (annotation instanceof RequestHeader){
					RequestHeader header = (RequestHeader) annotation;
					if (header.value().equals(NamesAndFormats.HEADER_USER_ID)){
						id = (Long)args[i];
					}
				}
			}
		}
		
		
		
		
		Long start = System.currentTimeMillis();
		Object ret = pjp.proceed();
		Long finish = System.currentTimeMillis();
		Double diff = (finish - start) / 1000.;
		bw.write(getFormattedLog(id, clazz, methodName, String.format(MESSAGE, diff)));
		bw.flush();
		return ret;
	}

	private String getFormattedLog(Long id, String clazz, String method, String message) {
		String currDT = dateFormat.format(new Date(System.currentTimeMillis()));
		return String.format(LOG_FORMAT, currDT, id, clazz, method, message);
	}
}
