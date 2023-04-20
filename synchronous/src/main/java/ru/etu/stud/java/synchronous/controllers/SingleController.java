package ru.etu.stud.java.synchronous.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;
import ru.etu.stud.java.synchronous.services.SingleService;

@RestController
public class SingleController {
    private final SingleService singleService;
    public  SingleController(SingleService singleService) {
        this.singleService = singleService;
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DatabaseEntity getOneByApiId(@PathVariable int id) {
        return singleService.getData(id);
    }
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DatabaseEntity> getAll() {
        return singleService.getData();
    }
}
