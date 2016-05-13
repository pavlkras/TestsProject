package main.java.security;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import main.java.entities.CredentialsEntity;

public class JwtUserDetailsService implements UserDetailsService {

	@PersistenceContext(unitName="HRTrueTestBES")
	EntityManager em;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Query query = em.createQuery("SELECT c FROM CredentialsEntity c WHERE login = :email")
				.setParameter("email", username);
		CredentialsEntity user = null;
		try {
			user = (CredentialsEntity) query.getSingleResult();
		} catch (NoResultException e) {
			throw new UsernameNotFoundException("login doesn't exist");
		}
		
		return JwtUserFactory.create(CredentialsEntity.convertToCredentialsData(user));
	}

}
