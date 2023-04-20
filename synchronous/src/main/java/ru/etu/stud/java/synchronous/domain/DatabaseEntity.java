package ru.etu.stud.java.synchronous.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "database_entity")
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

    public void setTextId(Long id) {
        this.textId = id;
    }

    public Long getTextId() {
        return textId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String text) {
        this.textContent = text;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }
}
