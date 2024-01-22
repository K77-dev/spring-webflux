package br.com.k77.webflux.wf.repository;

import br.com.k77.webflux.wf.entity.Todo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends ReactiveCrudRepository<Todo, Long> {}
