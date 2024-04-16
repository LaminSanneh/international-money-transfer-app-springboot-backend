package internationalmoneytransferapp.Backend.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTTokenGenerator {

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + ConfigConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
            .subject(username)
            .issuedAt(currentDate)
            .expiration(expireDate)
            .signWith(key(), Jwts.SIG.HS256).compact();

        return token;
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(ConfigConstants.JWT_SECRET));
    }

    public String getUsernameFromJWT(String jwt) {

        Claims claims = Jwts.parser().verifyWith(key()).build().parseSignedClaims(jwt).getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String jwt) {

        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(jwt);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
