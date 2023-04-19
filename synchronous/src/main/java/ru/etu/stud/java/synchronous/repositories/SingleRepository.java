package ru.etu.stud.java.synchronous.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;

@Repository
public interface SingleRepository extends CrudRepository<DatabaseEntity, Long> {
}
