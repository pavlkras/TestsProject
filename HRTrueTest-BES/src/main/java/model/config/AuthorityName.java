package main.java.model.config;

public enum AuthorityName {
	ROLE_COMPANY((byte)0b1), 
	ROLE_USER((byte)0b10), 
	ROLE_CANDIDATE((byte)0b100);
	
	private final Byte code;
	
	private AuthorityName(Byte code){
		this.code = code;
	}
	
	public Byte code(){
		return code;
	}
}
