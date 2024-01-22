package br.com.k77.webflux.wf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String username = jwtUtils.getUsername(token);
        if(username != null){
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, username);
            return Mono.just(auth);
        }else {
            return Mono.empty();
        }
    }
}
