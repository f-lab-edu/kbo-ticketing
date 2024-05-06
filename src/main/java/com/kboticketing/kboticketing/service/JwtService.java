package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.exception.CustomException;
import com.kboticketing.kboticketing.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author hazel
 */
@Service("JwtService")
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    private final static String issuer = "kbo-ticketing";

    public String generateJwt(int userId) {

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date oneDayLater = calendar.getTime();

        return Jwts.builder()
                   .header()
                   .and()
                   .issuer(issuer)
                   .subject(String.valueOf(userId))
                   .issuedAt(now)
                   .expiration(oneDayLater)
                   .signWith(getSigningKey())
                   .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
    }

    public String validateJwt(String jwt) {

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                                        .verifyWith(getSigningKey())
                                        .build()
                                        .parseSignedClaims(jwt);
            return claimsJws.getPayload()
                            .get("sub", String.class);

        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (JwtException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }
}
