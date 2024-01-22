package br.com.k77.webflux.wf.security;

import br.com.k77.webflux.wf.entity.User;
import br.com.k77.webflux.wf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class AuthHandler {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthHandler(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> signUp(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return userMono.map(u -> {
                    User userPsss = new User(u.getName(), u.getPassword());
                    userPsss.setPassword(encoder.encode(u.getPassword()));
                    return userPsss;
                })
                .flatMap(this.repository::save)
                .flatMap(user -> ServerResponse.ok().body(BodyInserters.fromValue(user)));
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(u -> this.repository.findByName(u.getName())
                        .flatMap(userFromDb -> {
                            if (encoder.matches(u.getPassword(), userFromDb.getPassword())) {
                                return ServerResponse.ok().body(BodyInserters.fromValue(jwtUtils.genToken(userFromDb)));
                            } else {
                                return ServerResponse.badRequest().body(BodyInserters.fromValue("Invalid credentials"));
                            }
                        })
                )
                .switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromValue("User does not exist")));
    }

}