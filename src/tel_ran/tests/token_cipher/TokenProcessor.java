package tel_ran.tests.token_cipher;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TokenProcessor {
	
	@Autowired
	AESCipher cipher;
	private static final String DELIMETER = ",";
	
	public String encodeIntoToken(long id, int validTimeInSec) {
		long currTimeStamp = new Date().getTime();
		long validUntilTimeStamp = currTimeStamp + validTimeInSec * 1000;
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(Long.toString(validUntilTimeStamp)); // array index #0
		strbuf.append(DELIMETER);
		strbuf.append(id); // array index #1
		String token = "";
		try {
			token = cipher.encrypt(strbuf.toString());
			//System.out.println("wasEncrypted: "+strbuf.toString());
		} catch (Exception e) {}
		return token;
	}

	public long decodeAndCheckToken(String token) {
		long companyId = -1; // ERRORSTATE = -1

		String wasDecrypted = "";
		try {
			wasDecrypted = cipher.decrypt(token);
			//System.out.println("wasDecrypted: "+wasDecrypted);
			
			String [] array = wasDecrypted.split(DELIMETER);
			long currTimeStamp = new Date().getTime();
			long validUntilTimeStamp = Long.parseLong(array[0]);
			if(validUntilTimeStamp > currTimeStamp){
				companyId = Long.parseLong(array[1]);
			}
		} catch (Exception e) {}
		return companyId;
	}
	
	public String encodeRoleToken(long id, int role, int validTimeInSec) {
		long currentTimeStamp = System.currentTimeMillis();
		long validUntilTime = currentTimeStamp + validTimeInSec * 1000;
		StringBuilder strb = new StringBuilder();
		strb.append(validUntilTime); //index 0
		strb.append(DELIMETER);
		strb.append(role);//index 1
		strb.append(DELIMETER);
		strb.append(id);//index 2
		String token = "";
		try {
			token = cipher.encrypt(strb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public User decodeRoleToken(String token) {
		int role = -1; //ERRORSTATE
		long id = -1; // ERRORSTATE = -1;
		String wasDecrypted = "";
		
		try {
			wasDecrypted = cipher.decrypt(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] array = wasDecrypted.split(DELIMETER);
		long validUntilTime = Long.parseLong(array[0]);
		long currentTime = System.currentTimeMillis();
		if(validUntilTime > currentTime) {
			role = Integer.parseInt(array[1]);
			id = Long.parseLong(array[2]);
		}
	
		return new User(id, role);	
	}

}
