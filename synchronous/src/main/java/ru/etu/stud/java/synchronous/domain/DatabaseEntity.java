package ru.etu.stud.java.synchronous.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "database_entity")
public class DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long text_id;
    private String text_content;
    public DatabaseEntity() {}
    public DatabaseEntity(String text_content) {
        this.text_content = text_content;
    }

    public void setText_id(Long id) {
        this.text_id = id;
    }

    public Long getText_id() {
        return text_id;
    }

    public String getText_content() {
        return text_content;
    }

    public void setText_content(String text) {
        this.text_content = text;
    }
}
