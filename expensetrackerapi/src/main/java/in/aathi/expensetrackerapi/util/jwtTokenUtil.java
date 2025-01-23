package in.aathi.expensetrackerapi.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class jwtTokenUtil {
	
	private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	@Value("${jwt.secret}")
	private String secret;
	
	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes); // Convert the secret to Key
    }

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		
		return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey()) // Use Key object and SignatureAlgorithm
                .compact();
	}

	public String getUsernameFromToken(String jwtToken) {
		return getClaimFromToken(jwtToken,Claims::getSubject);
	}
	
//	private<T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//		return claimsResolver.apply(claims);
//	}
	
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser() // Use parserBuilder()
                .verifyWith(getSigningKey()) // Use Key object for signing
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

	public boolean validateToken(String jwtToken, UserDetails userDetails) {
		
		final String username = getUsernameFromToken(jwtToken);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
	}

	private boolean isTokenExpired(String jwtToken) {
		final Date expiration = getExpirationDateFromToken(jwtToken);
		return expiration.before(new Date());
	}

	private Date getExpirationDateFromToken(String jwtToken) {
		return getClaimFromToken(jwtToken, Claims::getExpiration);
	}
	
}
