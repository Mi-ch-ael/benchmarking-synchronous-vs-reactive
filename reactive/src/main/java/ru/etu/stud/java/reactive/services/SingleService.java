package ru.etu.stud.java.reactive.services;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.etu.stud.java.reactive.domain.DatabaseEntity;
import ru.etu.stud.java.reactive.repositories.SingleRepository;

import java.util.concurrent.TimeUnit;

@Service
public class SingleService {
    private final SingleRepository repository;
    private final MeterRegistry registry;
    public SingleService(SingleRepository repository, MeterRegistry registry) {
        this.repository = repository;
        this.registry = registry;
        Timer.builder("reactive.first.publish.one").register(registry);
        Timer.builder("reactive.first.publish.all").register(registry);
    }
    public Mono<DatabaseEntity> getData(long id) {
        Mono<DatabaseEntity> response = repository.findById(id);
        response = response.elapsed().flatMap(tuple -> {
            registry.timer("reactive.first.publish.one")
                    .record(tuple.getT1(), TimeUnit.MILLISECONDS);
            return Mono.just(tuple.getT2());
        });
        return response;
    }
    public Flux<DatabaseEntity> getData() {
        Flux<DatabaseEntity> response = repository.findAll();
        response = response.elapsed().switchOnFirst(
                (signal, flux) -> {
                    registry.timer("reactive.first.publish.all")
                            .record(signal.get().getT1(), TimeUnit.MILLISECONDS);
                    return flux;
                }
        ).flatMap(tuple -> Mono.just(tuple.getT2()));
        return response;
    }
}
