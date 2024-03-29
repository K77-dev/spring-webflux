package br.com.k77.webflux.wf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class Config {

    @Bean
    public RouterFunction<ServerResponse> auth(AuthHandler handler){
        return RouterFunctions.route(POST("/sign-up").and(accept(MediaType.APPLICATION_JSON)), handler::signUp)
                .andRoute(POST("/login").and(accept(MediaType.APPLICATION_JSON)), handler::login);
    }
}
