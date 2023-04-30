package ru.etu.stud.java.synchronous.services;

import org.springframework.stereotype.Service;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;
import ru.etu.stud.java.synchronous.repositories.SingleRepository;
import ru.etu.stud.java.synchronous.wrappers.GaugeGetAllWrapper;
import ru.etu.stud.java.synchronous.wrappers.GaugeGetOneWrapper;

@Service
public class SingleService {
    private final SingleRepository repository;
    private final GaugeGetOneWrapper gaugeGetOneWrapper;
    private final GaugeGetAllWrapper gaugeGetAllWrapper;

    public SingleService(SingleRepository repository,
                         GaugeGetOneWrapper gaugeGetOneWrapper,
                         GaugeGetAllWrapper gaugeGetAllWrapper) {
        this.repository = repository;
        this.gaugeGetOneWrapper = gaugeGetOneWrapper;
        this.gaugeGetAllWrapper = gaugeGetAllWrapper;
    }
    public DatabaseEntity getData(long id) {
        gaugeGetOneWrapper.cpuTimeHolder().startMeasure(Thread.currentThread());
        DatabaseEntity response = repository.findByTextId(id).orElseThrow();
        gaugeGetOneWrapper.cpuTimeHolder().endMeasure(Thread.currentThread());
        gaugeGetOneWrapper.gauge().measure();
        return response;
    }
    public Iterable<DatabaseEntity> getData() {
        gaugeGetAllWrapper.cpuTimeHolder().startMeasure(Thread.currentThread());
        Iterable<DatabaseEntity> response = repository.findAll();
        gaugeGetAllWrapper.cpuTimeHolder().endMeasure(Thread.currentThread());
        gaugeGetOneWrapper.gauge().measure();
        return response;
    }
}
