package main.java.security.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.java.security.dao.JwtUser;

public class JwtUtil {

    //@Value("${jwt.secret}")
    private String secret = "signing";
    
    private Long expiration = 30/*mins*/ * 60L/*secs*/;
    
    private static final String CLAIM_KEY_USERNAME = Claims.SUBJECT;
    private static final String CLAIM_KEY_ID = Claims.ID;
    private static final String CLAIM_KEY_CREATED = Claims.ISSUED_AT;
    private static final String CLAIM_KEY_ROLES = "roles";
    

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     * 
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(JwtUser u) {
    	Set<String> roles = new HashSet<>();
    	for (GrantedAuthority authority : u.getAuthorities()){
    		roles.add(authority.getAuthority());
    	}
    	
        Claims claims = Jwts.claims()
        		.setSubject(u.getUsername())
        		.setId(u.getId().toString())
        		.setIssuedAt(new Date());
        		claims.put(CLAIM_KEY_ROLES, roles);

        return generateToken(claims);
    }
    
    private String generateToken(Claims claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

    public Long getIdFromToken(String token){
    	Long id;
    	try {
    		final Claims claims = getClaimsFromToken(token);
    		id = Long.parseLong(claims.getId());
    	} catch (Exception e){
    		id = null;
    	}
    	
    	return id;
    }
    
	public String getUsernameFromToken(String token) {
    	String username;
    	try {
    		final Claims claims = getClaimsFromToken(token);
    		username = claims.getSubject();
    	} catch (Exception e) {
    		username = null;
    	}
    	
    	return username;
    }
    
    public Date getCreatedDateFromToken(String token) {
    	Date created;
    	try {
    		final Claims claims = getClaimsFromToken(token);
    		created = claims.getIssuedAt();
    	} catch (Exception e) {
    		created = null;
    	}
    	
    	return created;
    }
    
    public Date getExpirationDateFromToken(String token) {
    	Date expiration;
    	try {
    		final Claims claims = getClaimsFromToken(token);
    		expiration = claims.getExpiration();
    	} catch (Exception e) {
    		expiration = null;
    	}
    	
    	return expiration;
    }

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		
		return claims;
	}
	
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}
	
	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset){
		final Date created = getCreatedDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& !isTokenExpired(token);
	}
	
	public String refreshToken(String token){
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.setIssuedAt(new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		JwtUser u = (JwtUser)userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getCreatedDateFromToken(token);
		return username.equals(u.getUsername())
				&& !isTokenExpired(token)
				&& !isCreatedBeforeLastPasswordReset(created, u.getLastPasswordResetDate());
	}
}
