package com.basel.FinalProject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenProvider
{
    @Value("${app.jwt-secret}")
    private  String SECRET_KEY;

    Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    String base64SecretKey = Arrays.toString(secretKey.getEncoded());
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationIMs;
    public String getUsernameFromJWT(String jwtToken) {
        return extractCliam(jwtToken,Claims::getSubject);
    }
    // extract all the cliams To Be easier to take the
    public <T> T extractCliam(String token, Function<Claims,T> cliamsResolver){
        final Claims claims= extractAllClaims(token);
        return cliamsResolver.apply(claims);
    }
    //we want to ensure who send the token (who claims to be) from the signing key
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
//with extraCliams
public String genratetoken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationIMs))
            .signWith( SignatureAlgorithm.HS512,secretKey)
            .compact();
}
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=getUsernameFromJWT(token);
        return (username.equals(userDetails.getUsername()))&&!isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractCliam(token,Claims::getExpiration);
    }


    public String genratetoken(UserDetails userDetails){
        System.out.println("secretKey is ____________________"+secretKey.toString());

        return genratetoken(new HashMap<>(),userDetails);
    }
//    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
}
