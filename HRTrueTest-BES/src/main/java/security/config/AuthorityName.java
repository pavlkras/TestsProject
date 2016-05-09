package main.java.security.config;

public enum AuthorityName {
	ROLE_COMPANY(0x1), 
	ROLE_USER(0x10), 
	ROLE_CANDIDATE(0x100);
	
	private final Integer code;
	
	private AuthorityName(Integer code){
		this.code = code;
	}
	
	public Integer code(){
		return code;
	}
}
