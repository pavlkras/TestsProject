package main.java.security.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.stereotype.Component;

@Component
public class RolesMap extends LinkedHashMap<String, Integer> {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public RolesMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RolesMap(Map<? extends String, ? extends Integer> m) {
		super(m);
		// TODO Auto-generated constructor stub
	}
}
