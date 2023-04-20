package ru.etu.stud.java.reactive.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.etu.stud.java.reactive.domain.DatabaseEntity;
import ru.etu.stud.java.reactive.repositories.SingleRepository;

@Service
public class SingleService {
    private final SingleRepository repository;
    public SingleService(SingleRepository repository) {
        this.repository = repository;
    }
    public Mono<DatabaseEntity> getData(int id) {
        return repository.findByApiId(id);
    }
    public Flux<DatabaseEntity> getData() {
        return repository.findAll();
    }
}
