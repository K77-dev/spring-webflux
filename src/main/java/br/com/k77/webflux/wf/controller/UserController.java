package br.com.k77.webflux.wf.controller;

import br.com.k77.webflux.wf.entity.User;
import br.com.k77.webflux.wf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Mono<User> save(@RequestBody User todo){
        return this.repository.save(todo);
    }

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable("id") Long id){
        return this.repository.findById(id);
    }

    @GetMapping()
    public Flux<User> getAll(){
        return this.repository.findAll();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> remove(@PathVariable("id") Long id){
        return this.repository.deleteById(id);
    }
}