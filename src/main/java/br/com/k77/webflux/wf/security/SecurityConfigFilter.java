package br.com.k77.webflux.wf.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfigFilter {

    @Autowired
    private AuthManager authManager;

    @Autowired
    private SecurityContext securityContext;

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .csrf().disable()
                .authenticationManager(authManager)
                .securityContextRepository(securityContext)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/sign-up/**", "/login/**").permitAll()
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passEncode(){
        return new BCryptPasswordEncoder();
    }

}