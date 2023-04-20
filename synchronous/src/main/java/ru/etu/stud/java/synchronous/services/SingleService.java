package ru.etu.stud.java.synchronous.services;

import org.springframework.stereotype.Service;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;
import ru.etu.stud.java.synchronous.repositories.SingleRepository;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class SingleService {
    private final SingleRepository repository;
    public SingleService(SingleRepository repository) {
        this.repository = repository;
    }
    public void populateRepositoryOnNormalStartup(int recordsNumber, int stringLength) {
        this.repository.deleteAll();
        for(int i = 0; i < recordsNumber; ++i) {
            DatabaseEntity entity = new DatabaseEntity(i+1, randomAsciiNonWhitespaceString(stringLength));
            this.repository.save(entity);
        }
    }
    private String randomAsciiNonWhitespaceString(int length) {
        StringBuilder resultBuilder = new StringBuilder(length);
        for(int i = 0; i < length; ++i) {
            resultBuilder.append( (char) ThreadLocalRandom.current().nextInt(0x21, 0x7F) );
        }
        return resultBuilder.toString();
    }
    public DatabaseEntity getData(int id) {
        return repository.findByApiId(id).orElseThrow();
    }
    public Iterable<DatabaseEntity> getData() {
        return repository.findAll();
    }
}
