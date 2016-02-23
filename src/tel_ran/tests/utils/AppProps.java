package tel_ran.tests.utils;

import org.springframework.stereotype.Service;

@Service
public class AppProps {
	private String hostname;
	private String clientname;

	
	
	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

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