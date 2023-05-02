package ru.etu.stud.java.synchronous.tools;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class CpuTimeHolder {
    private final ThreadMXBean threadBean;
    private final Logger logger = LoggerFactory.getLogger(CpuTimeHolder.class);
    private final MeterRegistry meterRegistry;
    private final Map<Long, Long> startCpuTimes = Collections.synchronizedMap(new HashMap<>());

    public CpuTimeHolder(MeterRegistry meterRegistry,
                         Map<String, String> actionTypesToTimerNames) {
        threadBean = ManagementFactory.getThreadMXBean();
        if(!threadBean.isThreadCpuTimeSupported()) {
            logger.error("Thread CPU time measurement not supported");
            throw new IllegalStateException("Thread CPU time measurement not supported");
        }
        if(!threadBean.isThreadCpuTimeEnabled()) {
            logger.info("Thread CPU time measurement disabled by default. Enabling now.");
            threadBean.setThreadContentionMonitoringEnabled(true);
        }
        this.meterRegistry = meterRegistry;
        Timer.builder(actionTypesToTimerNames.get("one")).register(meterRegistry);
        Timer.builder(actionTypesToTimerNames.get("all")).register(meterRegistry);
    }
    public void startMeasure(Thread thread) {
        long threadId = thread.getId();
        startCpuTimes.put(threadId, threadBean.getThreadCpuTime(threadId));
        logger.debug("Started measuring thread #%d \"%s\"".formatted(threadId, thread.getName()));
    }
    public void endMeasure(Thread thread, String timerName) {
        long threadId = thread.getId();
        meterRegistry.timer(timerName).record(
                threadBean.getThreadCpuTime(threadId) - startCpuTimes.get(threadId),
                TimeUnit.NANOSECONDS
        );
        logger.debug("Finished measuring thread #%d \"%s\"".formatted(threadId, thread.getName()));
    }
}
