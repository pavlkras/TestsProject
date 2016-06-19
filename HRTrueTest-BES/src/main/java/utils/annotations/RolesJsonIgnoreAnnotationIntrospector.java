package main.java.utils.annotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;

import main.java.security.dao.JwtUser;

public class RolesJsonIgnoreAnnotationIntrospector extends AnnotationIntrospector {

	/**
	 * 
	 */
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	@Override
	public Version version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasIgnoreMarker(AnnotatedMember m) {
		if (m.hasAnnotation(RolesJsonIgnore.class)){
			RolesJsonIgnore rji = m.getAnnotation(RolesJsonIgnore.class);
			if (rji.value() != null){
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal != null && principal instanceof JwtUser){
					JwtUser jwtUser = (JwtUser) principal;
					Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
					List<String> ignoreRoles = Arrays.asList(rji.value());
					for (String ignoreRole : ignoreRoles){
						if (authorities.contains(new SimpleGrantedAuthority(ignoreRole)))
							return true;
					}
				}
			}
		}
		return super.hasIgnoreMarker(m);
	}

	public RolesJsonIgnoreAnnotationIntrospector() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
