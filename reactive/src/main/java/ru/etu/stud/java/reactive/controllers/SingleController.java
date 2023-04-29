package ru.etu.stud.java.reactive.controllers;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.etu.stud.java.reactive.domain.DatabaseEntity;
import ru.etu.stud.java.reactive.services.SingleService;
import ru.etu.stud.java.reactive.tools.ThreadObserver;

@RestController
public class SingleController {
    private final SingleService singleService;
    private final ThreadObserver threadObserver;
    private final Gauge averageBlockTime;
    public  SingleController(SingleService singleService, ThreadObserver threadObserver, Gauge averageBlockTime) {
        this.singleService = singleService;
        this.threadObserver = threadObserver;
        this.averageBlockTime = averageBlockTime;
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "reactive.controller.one")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<DatabaseEntity> getOneByApiId(@PathVariable long id) {
        Mono<DatabaseEntity> response = this.singleService.getData(id);
        this.threadObserver.registerBlockedTime(Thread.currentThread());
        this.averageBlockTime.measure();
        return response;
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "reactive.controller.all")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DatabaseEntity> getAll() {
        return singleService.getData();
    }
}
