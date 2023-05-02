package ru.etu.stud.java.synchronous.configuration;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class TimerConfiguration {
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
    @Bean
    public Map<String, String> actionTypesToTimerNames() {
        return Map.of(
                "one", "synchronous.db.wait.one",
                "all", "synchronous.db.wait.all"
        );
    }
}