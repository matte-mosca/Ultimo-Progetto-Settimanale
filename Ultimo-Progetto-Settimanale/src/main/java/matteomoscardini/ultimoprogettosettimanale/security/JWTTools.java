package matteomoscardini.ultimoprogettosettimanale.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import matteomoscardini.ultimoprogettosettimanale.entities.User;
import matteomoscardini.ultimoprogettosettimanale.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user){
        return Jwts.builder().issuedAt(
                new Date(System.currentTimeMillis())
        ).expiration(new Date(System.currentTimeMillis()+1000*60*60*24*7))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    public void verifyToken (String token){
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Your token is wrong. Please try again");
        }
    }
    public String extractIdFromToken (String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(token).getPayload().getSubject();

    }
}
