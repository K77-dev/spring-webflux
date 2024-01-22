package br.com.k77.webflux.wf.security;

import br.com.k77.webflux.wf.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.function.Function;

@Component
public class JwtUtils implements Serializable {

    private final String MY_SECRET = "KELSENKLEBERSILVABRITO";
    public String getUsername(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(MY_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public String genToken(User user){
        return Jwts.builder()
                .setSubject(user.getName())
                .signWith(SignatureAlgorithm.HS256, MY_SECRET)
                .compact();
    }
}
