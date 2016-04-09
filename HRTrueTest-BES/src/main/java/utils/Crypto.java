package main.java.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public abstract class Crypto {
	public static String generateHash(String input){
		String salt = BCrypt.gensalt();
		
		String hash = BCrypt.hashpw(input, salt);
		return hash;
	}
	
	public static boolean matches(String rawInput, String hash){
		return BCrypt.checkpw(rawInput, hash);
	}
}
