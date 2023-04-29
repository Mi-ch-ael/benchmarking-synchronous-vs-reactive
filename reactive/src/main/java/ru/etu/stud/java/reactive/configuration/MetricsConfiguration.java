package ru.etu.stud.java.reactive.configuration;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.etu.stud.java.reactive.tools.GaugeGetAllWrapper;
import ru.etu.stud.java.reactive.tools.GaugeGetOneWrapper;
import ru.etu.stud.java.reactive.tools.ThreadObserver;

@Configuration
public class MetricsConfiguration {
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
    @Bean
    public GaugeGetOneWrapper averageBlockedTimeGetOne(MeterRegistry registry, ThreadObserver threadObserver) {
        return new GaugeGetOneWrapper(
                Gauge.builder(
                                "reactive.one.threadBlocked.average",
                                threadObserver,
                                ThreadObserver::averageBlockedTime
                        )
                        .description("Average time a thread spends blocked while serving /{id}")
                        .register(registry),
                threadObserver
        );
    }
    @Bean
    public GaugeGetAllWrapper averageBlockedTimeGetAll(MeterRegistry registry, ThreadObserver threadObserver) {
        return new GaugeGetAllWrapper(
                Gauge.builder(
                                "reactive.all.threadBlocked.average",
                                threadObserver,
                                ThreadObserver::averageBlockedTime
                        )
                        .description("Average time a thread spends blocked while serving /")
                        .register(registry),
                threadObserver
        );
    }
}
