package ru.etu.stud.java.reactive.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.etu.stud.java.reactive.domain.DatabaseEntity;
@Repository
public interface SingleRepository extends ReactiveCrudRepository<DatabaseEntity, Long> {
}
