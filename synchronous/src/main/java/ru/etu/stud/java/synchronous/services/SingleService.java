package ru.etu.stud.java.synchronous.services;

import org.springframework.stereotype.Service;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;
import ru.etu.stud.java.synchronous.repositories.SingleRepository;

@Service
public class SingleService {
    private final SingleRepository repository;
    public SingleService(SingleRepository repository) {
        this.repository = repository;
    }

    public DatabaseEntity getData(long id) {
        return repository.findById(id).orElseThrow();
    }
    public Iterable<DatabaseEntity> getData() {
        return repository.findAll();
    }
}
