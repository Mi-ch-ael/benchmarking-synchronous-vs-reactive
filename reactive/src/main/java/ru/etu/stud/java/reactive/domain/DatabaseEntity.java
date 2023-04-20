package ru.etu.stud.java.reactive.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "database_entity")
@Data
public class DatabaseEntity {
    @Id
    private long textId;
    private String textContent;
}

