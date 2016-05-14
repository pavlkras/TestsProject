package main.java.utils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import main.java.entities.CredentialsEntity;
import main.java.security.JwtUserFactory;
import main.java.security.dao.JwtUser;

public class UserLoaderService {
	@PersistenceContext(unitName="HRTrueTestBES")
	EntityManager em;
	
	public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
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
