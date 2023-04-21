package ru.etu.stud.java.synchronous.repositories;


import io.micrometer.core.annotation.Timed;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;

import java.util.Optional;

@Repository
public interface SingleRepository extends CrudRepository<DatabaseEntity, Long> {
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "synchronous.repository.one")
    Optional<DatabaseEntity> findByTextId(long id);
    @Override
    @NotNull
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "synchronous.repository.all")
    Iterable<DatabaseEntity> findAll();
}
