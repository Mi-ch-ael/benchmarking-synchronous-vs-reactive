package ru.etu.stud.java.reactive.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import ru.etu.stud.java.reactive.domain.DatabaseEntity;
import ru.etu.stud.java.reactive.services.SingleService;

import java.util.concurrent.TimeUnit;

@RestController
public class SingleController {
    private final SingleService singleService;
    private final MeterRegistry meterRegistry;
    public  SingleController(SingleService singleService, MeterRegistry meterRegistry) {
        this.singleService = singleService;
        this.meterRegistry = meterRegistry;
        Timer.builder("reactive.controller.one").register(meterRegistry);
        Timer.builder("reactive.controller.all").register(meterRegistry);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<DatabaseEntity> getOneByApiId(@PathVariable long id) {
        long startTime = System.currentTimeMillis();
        Mono<DatabaseEntity> response = singleService.getData(id);
        response = response.doFinally(signalType -> {
            if(signalType == SignalType.ON_COMPLETE) {
                meterRegistry.timer("reactive.controller.one").record(
                        System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS
                );
            }
        });
        return response;
    }
    @GetMapping(value = "/", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DatabaseEntity> getAll() {
        long startTime = System.currentTimeMillis();
        Flux<DatabaseEntity> response = singleService.getData();
        response = response.doFinally(signalType -> {
            if(signalType == SignalType.ON_COMPLETE) {
                meterRegistry.timer("reactive.controller.all").record(
                        System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS
                );
            }
        });
        return response;
    }
}
