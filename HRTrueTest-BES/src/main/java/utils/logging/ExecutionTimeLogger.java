package main.java.utils.logging;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestHeader;

import main.java.model.config.NamesAndFormats;

/**
 * @author Pavel
 *
 */
public class ExecutionTimeLogger extends AbstractFileLogger {

	private static final String MESSAGE = "Executed in %f sec.";
	
	public ExecutionTimeLogger(String fileName) {
		super(fileName);
	}

	@Override
	public Object makeLog(ProceedingJoinPoint pjp) throws Throwable {
		Long id = null;
		String ip = null;
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
		bw.write(getFormattedLog(id, ip, clazz, methodName, String.format(MESSAGE, diff)));
		bw.flush();
		return ret;
	}
}
