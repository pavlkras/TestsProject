package tel_ran.tests.utils;

import org.springframework.stereotype.Service;

@Service
public class AppProps {
	private String hostname;

	public String getHostname() {
		return hostname;
	}
	
	public String getBesName() {
		return hostname+"/TestsProjectBes";
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
}