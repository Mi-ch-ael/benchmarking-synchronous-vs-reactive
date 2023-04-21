package ru.etu.stud.java.synchronous.services;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Service;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;
import ru.etu.stud.java.synchronous.repositories.SingleRepository;

@Service
public class SingleService {
    private final SingleRepository repository;
    public SingleService(SingleRepository repository) {
        this.repository = repository;
    }

    @Timed("synchronous.one")
    public DatabaseEntity getData(long id) {
        return repository.findById(id).orElseThrow();
    }
    @Timed("synchronous.all")
    public Iterable<DatabaseEntity> getData() {
        return repository.findAll();
    }
}
