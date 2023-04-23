package ru.etu.stud.java.synchronous.configuration;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.etu.stud.java.synchronous.tools.ThreadObserver;

@Configuration
public class MetricsConfiguration {
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
    @Bean
    public Gauge averageThreadBlockedTime(MeterRegistry registry, ThreadObserver threadObserver) {
        return Gauge.builder(
                    "synchronous.one.threadBlocked.average",
                    threadObserver,
                    ThreadObserver::averageBlockedTime
                )
                .description("Average time a thread spends blocked while serving /{id}")
                .register(registry);
    }
}