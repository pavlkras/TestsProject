package main.java.security;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationTimeout {
	private Map<String, Long> lastAccess = new HashMap<>();
	private static AuthenticationTimeout instance = null;
	private static final long TIMEOUT = 30 * 60 * 1000;
	
	private AuthenticationTimeout(){}
	
	public static AuthenticationTimeout getInstance(){
		if (instance == null)
			instance = new AuthenticationTimeout();
		
		return instance;
	}
	
	public boolean isActive(String user){
		boolean ret = false;
		if (lastAccess.containsKey(user)) {
			long curTime = System.currentTimeMillis();
			ret = curTime < TIMEOUT + lastAccess.get(user);
		}
		
		return ret;
	}
	
	public void updateTimeout(String user){
		long curTime = System.currentTimeMillis();
		lastAccess.put(user, curTime);
	}
	
	public boolean revokeAccess(String token){
		return lastAccess.remove(token) != null;
	}
}
