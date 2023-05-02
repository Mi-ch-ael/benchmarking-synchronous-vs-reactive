package ru.etu.stud.java.synchronous.services;

import org.springframework.stereotype.Service;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;
import ru.etu.stud.java.synchronous.repositories.SingleRepository;
import ru.etu.stud.java.synchronous.tools.CpuTimeHolder;

import java.util.Map;

@Service
public class SingleService {
    private final SingleRepository repository;
    private final CpuTimeHolder cpuTimeHolder;
    private final Map<String, String> actionTypesToTimerNames;

    public SingleService(SingleRepository repository,
                         Map<String, String> actionTypesToTimerNames,
                         CpuTimeHolder cpuTimeHolder) {
        this.repository = repository;
        this.actionTypesToTimerNames = actionTypesToTimerNames;
        this.cpuTimeHolder = cpuTimeHolder;
    }
    public DatabaseEntity getData(long id) {
        cpuTimeHolder.startMeasure(Thread.currentThread());
        DatabaseEntity response = repository.findByTextId(id).orElseThrow();
        cpuTimeHolder.endMeasure(Thread.currentThread(), actionTypesToTimerNames.get("one"));
        return response;
    }
    public Iterable<DatabaseEntity> getData() {
        cpuTimeHolder.startMeasure(Thread.currentThread());
        Iterable<DatabaseEntity> response = repository.findAll();
        cpuTimeHolder.endMeasure(Thread.currentThread(), actionTypesToTimerNames.get("all"));
        return response;
    }
}
