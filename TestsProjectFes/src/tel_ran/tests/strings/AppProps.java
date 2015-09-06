package tel_ran.tests.strings;

import org.springframework.stereotype.Service;

@Service
public class AppProps {
	private String hostname;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
}