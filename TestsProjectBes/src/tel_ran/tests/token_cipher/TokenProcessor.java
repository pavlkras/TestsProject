package tel_ran.tests.token_cipher;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tel_ran.tests.services.common.ICommonData;

@Component
public class TokenProcessor {
	
	@Autowired
	AESCipher cipher;
	
	public String encodeIntoToken(long id, int validTimeInSec) {
		long currTimeStamp = new Date().getTime();
		long validUntilTimeStamp = currTimeStamp + validTimeInSec * 1000;
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(Long.toString(validUntilTimeStamp)); // array index #0
		strbuf.append(ICommonData.delimiter);
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
			
			String [] array = wasDecrypted.split(ICommonData.delimiter);
			long currTimeStamp = new Date().getTime();
			long validUntilTimeStamp = Long.parseLong(array[0]);
			if(validUntilTimeStamp > currTimeStamp){
				companyId = Long.parseLong(array[1]);
			}
		} catch (Exception e) {}
		return companyId;
	}

}
