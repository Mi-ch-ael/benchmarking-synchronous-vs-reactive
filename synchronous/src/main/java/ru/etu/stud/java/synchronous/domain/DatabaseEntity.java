package ru.etu.stud.java.synchronous.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "database_entity")
@Data
public class DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long textId;
    private int apiId;
    private String textContent;
    public DatabaseEntity() {}
    public DatabaseEntity(int id, String textContent) {
        this.apiId = id;
        this.textContent = textContent;
    }
}
