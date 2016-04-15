package main.java.model.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.stereotype.Component;

@Component
public class ActivityTypeMap extends LinkedHashMap<Integer, String> {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public ActivityTypeMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActivityTypeMap(Map<? extends Integer, ? extends String> m) {
		super(m);
		// TODO Auto-generated constructor stub
	}
	
	
}
