package main.java.utils.logging;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;

public class CallsLogger extends AbstractFileLogger {
	
	private static final String MESSAGE = "Request: %s%nResponse: %d";
	
	public CallsLogger(String fileName) {
		super(fileName);
	}

	@Override
	public Object makeLog(ProceedingJoinPoint pjp) throws Throwable {
		Long id = null;
		String ip = null;
		String clazz = pjp.getTarget().getClass().getSimpleName();
		String methodName = pjp.getSignature().getName();
		String szRequest = null;
		Integer responseCode = null;

		Object[] args = pjp.getArgs();
		for (Object arg : args){
			if (arg instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest)arg;
				szRequest = getHeadersInfo(request).toString();
			}
		}
		
		Object ret = pjp.proceed();
		

		for (Object arg : args){
			if (arg instanceof HttpServletResponse){
				HttpServletResponse response = (HttpServletResponse)arg;
				responseCode = response.getStatus();
			}
		}
		
		bw.write(getFormattedLog(id, ip, clazz, methodName, String.format(MESSAGE, szRequest, responseCode)));
		bw.flush();
		return ret;
	}

	private static Map<String, String> getHeadersInfo(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	  }
	
}
