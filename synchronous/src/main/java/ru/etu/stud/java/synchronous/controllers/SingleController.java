package ru.etu.stud.java.synchronous.controllers;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.etu.stud.java.synchronous.tools.ThreadObserver;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;
import ru.etu.stud.java.synchronous.services.SingleService;

@RestController
public class SingleController {
    private final SingleService singleService;
    private final ThreadObserver threadObserver;
    private final Gauge averageBlockTime;
    private final Logger logger = LoggerFactory.getLogger(ThreadObserver.class);
    public SingleController(SingleService singleService, ThreadObserver threadObserver, Gauge averageBlockTime) {
        this.singleService = singleService;
        this.threadObserver = threadObserver;
        this.averageBlockTime = averageBlockTime;
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "synchronous.controller.one")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DatabaseEntity getOneByApiId(@PathVariable long id) {
        DatabaseEntity response = this.singleService.getData(id);
        this.threadObserver.registerBlockedTime(Thread.currentThread());
        this.averageBlockTime.measure();
        return response;
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "synchronous.controller.all")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DatabaseEntity> getAll() {
        return singleService.getData();
    }
}
