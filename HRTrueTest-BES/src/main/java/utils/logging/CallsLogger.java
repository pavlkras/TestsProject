package main.java.utils.logging;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;

public class CallsLogger extends AbstractFileLogger {
	/* format: "Timestamp ip request responseCode */
	private static final String LOG_FORMAT = "%s\t%17s%nRequest:%n%s%nResponse code: %d%n%n";
	
	public CallsLogger(String fileName) {
		super(fileName);
	}

	@Override
	public Object makeLog(ProceedingJoinPoint pjp) throws Throwable {
		String ip = null;
		String szRequest = null;
		Integer responseCode = null;
		
		Object[] args = pjp.getArgs();
		for (Object arg : args){
			if (arg instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest)arg;
				ip = request.getHeader("X-FORWARDED-FOR");
				if (ip == null){
					ip = request.getRemoteAddr();
				}
				szRequest = getFormattedRequest(request);
			}
		}
		
		Object ret = pjp.proceed();
		

		for (Object arg : args){
			if (arg instanceof HttpServletResponse){
				HttpServletResponse response = (HttpServletResponse)arg;
				responseCode = response.getStatus();
			}
		}
		
		bw.write(getFormattedLog(ip, szRequest, responseCode));
		bw.flush();
		return ret;
	}
	
	private static String getFormattedLog(String ip, String request, Integer responseCode) {
		String currDT = dateFormat.format(new Date(System.currentTimeMillis()));
		return String.format(LOG_FORMAT, currDT, ip, request, responseCode);
	}
	
	private static String getFormattedRequest(HttpServletRequest request) {
		String method = request.getMethod();
		String path = getFullURL(request);
		String protocol = request.getProtocol();
		String headers = getFormattedHeaders(request);
		return String.format("%s\t%s\t%s%n%s", method, path, protocol, headers);
	}

	private static String getFormattedHeaders(HttpServletRequest request) {
		Map<String, String> headersMap = getHeadersInfo(request);
		StringBuilder sb = new StringBuilder();
		for (String header : headersMap.keySet()){
			sb.append(String.format("%s: %s%n", header, headersMap.get(header)));
		}
		return sb.toString();
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
	
	public static String getFullURL(HttpServletRequest request) {
	    StringBuffer requestURL = new StringBuffer(request.getServletPath());
	    String queryString = request.getQueryString();

	    if (queryString == null) {
	        return requestURL.toString();
	    } else {
	        return requestURL.append('?').append(queryString).toString();
	    }
	}
	
}
