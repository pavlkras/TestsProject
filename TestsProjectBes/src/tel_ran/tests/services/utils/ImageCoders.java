package tel_ran.tests.services.utils;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class ImageCoders {
	
public static String decode64(String coded){
		
		Decoder decoder = Base64.getDecoder();
		return new String (decoder.decode(coded));
		
	}
	
	public static String encode64(String uncoded){
		
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(uncoded.getBytes());
		
	}

}
