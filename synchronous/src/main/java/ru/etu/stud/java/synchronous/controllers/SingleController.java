package ru.etu.stud.java.synchronous.controllers;

import io.micrometer.core.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;
import ru.etu.stud.java.synchronous.services.SingleService;
import ru.etu.stud.java.synchronous.tools.GaugeGetAllWrapper;
import ru.etu.stud.java.synchronous.tools.GaugeGetOneWrapper;

@RestController
public class SingleController {
    private final SingleService singleService;
    private final GaugeGetOneWrapper gaugeGetOneWrapper;
    private final GaugeGetAllWrapper gaugeGetAllWrapper;
    public SingleController(SingleService singleService,
                            GaugeGetOneWrapper gaugeGetOneWrapper,
                            GaugeGetAllWrapper gaugeGetAllWrapper) {
        this.singleService = singleService;
        this.gaugeGetOneWrapper = gaugeGetOneWrapper;
        this.gaugeGetAllWrapper = gaugeGetAllWrapper;
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "synchronous.controller.one")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DatabaseEntity getOneByApiId(@PathVariable long id) {
        DatabaseEntity response = this.singleService.getData(id);
        this.gaugeGetOneWrapper.threadObserver().registerBlockedTime(Thread.currentThread());
        this.gaugeGetOneWrapper.gauge().measure();
        return response;
    }
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.99}, value = "synchronous.controller.all")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DatabaseEntity> getAll() {
        Iterable<DatabaseEntity> response = this.singleService.getData();
        this.gaugeGetAllWrapper.threadObserver().registerBlockedTime(Thread.currentThread());
        this.gaugeGetAllWrapper.gauge().measure();
        return response;
    }
}
