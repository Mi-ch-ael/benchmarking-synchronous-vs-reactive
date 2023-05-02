package ru.etu.stud.java.reactive.controllers;

import io.micrometer.core.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.etu.stud.java.reactive.domain.DatabaseEntity;
import ru.etu.stud.java.reactive.services.SingleService;

@RestController
public class SingleController {
    private final SingleService singleService;
    public  SingleController(SingleService singleService) {
        this.singleService = singleService;
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "reactive.controller.one")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<DatabaseEntity> getOneByApiId(@PathVariable long id) {
        return singleService.getData(id);
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "reactive.controller.all")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DatabaseEntity> getAll() {
        return singleService.getData();
    }
}
