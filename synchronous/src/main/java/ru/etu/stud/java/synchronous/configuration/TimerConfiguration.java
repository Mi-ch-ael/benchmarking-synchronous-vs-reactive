package ru.etu.stud.java.synchronous.configuration;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.etu.stud.java.synchronous.tools.CpuTimeHolder;
import ru.etu.stud.java.synchronous.wrappers.GaugeGetAllWrapper;
import ru.etu.stud.java.synchronous.wrappers.GaugeGetOneWrapper;

@Configuration
public class TimerConfiguration {
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
    @Bean
    public GaugeGetOneWrapper gaugeGetOneWrapper(MeterRegistry registry, CpuTimeHolder cpuTimeHolder) {
        return new GaugeGetOneWrapper(
                cpuTimeHolder,
                Gauge.builder(
                        "synchronous.one.waitDB.ms",
                        cpuTimeHolder,
                        CpuTimeHolder::last
                ).description("CPU time wait for /{id} request").register(registry)
        );
    }
    @Bean
    public GaugeGetAllWrapper gaugeGetAllWrapper(MeterRegistry registry, CpuTimeHolder cpuTimeHolder) {
        return new GaugeGetAllWrapper(
                cpuTimeHolder,
                Gauge.builder(
                        "synchronous.all.waitDB.ms",
                        cpuTimeHolder,
                        CpuTimeHolder::last
                ).description("CPU time wait for /{id} request").register(registry)
        );
    }
}