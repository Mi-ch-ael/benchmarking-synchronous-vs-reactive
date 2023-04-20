package ru.etu.stud.java.reactive.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.etu.stud.java.reactive.domain.DatabaseEntity;
@Repository
public interface SingleRepository extends ReactiveCrudRepository<DatabaseEntity, Long> {
    Mono<DatabaseEntity> findByApiId(int id);
}
