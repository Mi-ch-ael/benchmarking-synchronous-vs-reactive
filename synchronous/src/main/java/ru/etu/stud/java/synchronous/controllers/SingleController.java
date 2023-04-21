package ru.etu.stud.java.synchronous.controllers;

import io.micrometer.core.annotation.Timed;
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
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "synchronous.controller.one")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DatabaseEntity getOneByApiId(@PathVariable long id) {
        return singleService.getData(id);
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "synchronous.controller.all")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DatabaseEntity> getAll() {
        return singleService.getData();
    }
}
