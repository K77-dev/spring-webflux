package br.com.k77.webflux.wf.controller;

import br.com.k77.webflux.wf.repository.TodoRepository;
import br.com.k77.webflux.wf.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Mono<Todo> save(@RequestBody Todo todo){
        return this.repository.save(todo);
    }

    @GetMapping("/{id}")
    public Mono<Todo> findById(@PathVariable("id") Long id){
        return this.repository.findById(id);
    }

    @GetMapping()
    public Flux<Todo> getAll(){
        return this.repository.findAll();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> remove(@PathVariable("id") Long id){
        return this.repository.deleteById(id);
    }
}