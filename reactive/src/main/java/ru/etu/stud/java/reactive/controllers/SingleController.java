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
import ru.etu.stud.java.reactive.tools.GaugeGetAllWrapper;
import ru.etu.stud.java.reactive.tools.GaugeGetOneWrapper;

@RestController
public class SingleController {
    private final SingleService singleService;
    private final GaugeGetOneWrapper gaugeGetOneWrapper;
    private final GaugeGetAllWrapper gaugeGetAllWrapper;
    public  SingleController(SingleService singleService,
                             GaugeGetOneWrapper gaugeGetOneWrapper,
                             GaugeGetAllWrapper gaugeGetAllWrapper) {
        this.singleService = singleService;
        this.gaugeGetOneWrapper = gaugeGetOneWrapper;
        this.gaugeGetAllWrapper = gaugeGetAllWrapper;
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "reactive.controller.one")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<DatabaseEntity> getOneByApiId(@PathVariable long id) {
        Mono<DatabaseEntity> response = this.singleService.getData(id);
        this.gaugeGetOneWrapper.threadObserver().registerBlockedTime(Thread.currentThread());
        this.gaugeGetOneWrapper.gauge().measure();
        return response;
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "reactive.controller.all")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DatabaseEntity> getAll() {
        Flux<DatabaseEntity> response = singleService.getData();
        this.gaugeGetAllWrapper.threadObserver().registerBlockedTime(Thread.currentThread());
        this.gaugeGetAllWrapper.gauge().measure();
        return response;
    }
}
