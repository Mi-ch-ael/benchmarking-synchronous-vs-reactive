package ru.etu.stud.java.reactive.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;

@Component
public class ThreadObserver {
    private final ThreadMXBean threadBean;
    private final Logger logger = LoggerFactory.getLogger(ThreadObserver.class);
    private final Map<Long, Long> lastTotalBlockTimes = Collections.synchronizedMap(new HashMap<>());
    private final List<Long> blockTimes = Collections.synchronizedList(new ArrayList<>());
    public ThreadObserver() {
        threadBean = ManagementFactory.getThreadMXBean();
        if(!threadBean.isThreadContentionMonitoringSupported()) {
            logger.error("Thread monitoring not supported");
            throw new IllegalStateException("Thread monitoring not supported");
        }
        if(!threadBean.isThreadContentionMonitoringEnabled()) {
            logger.info("Thread monitoring disabled by default. Enabling now.");
            threadBean.setThreadContentionMonitoringEnabled(true);
        }
    }
    public void registerBlockedTime(Thread thread) {
        Long threadId = thread.getId();
        this.lastTotalBlockTimes.putIfAbsent(threadId, 0L);
        long currentTotalDelay = this.threadBean.getThreadInfo(threadId).getBlockedTime();
        long previousTotalDelay = this.lastTotalBlockTimes.get(threadId);
        if(previousTotalDelay > currentTotalDelay) {
            this.logger.debug("Thread id %d likely reused".formatted(threadId));
            previousTotalDelay = 0;
        }
        this.lastTotalBlockTimes.put(threadId, currentTotalDelay);
        this.logger.debug("Thread #%d spent %d ms blocked this time".formatted(threadId,
                currentTotalDelay - previousTotalDelay));
        this.blockTimes.add(currentTotalDelay - previousTotalDelay);
    }
    public Double averageBlockedTime() {
        int size = this.blockTimes.size();
        long total = 0;
        for(int i = 0; i < size; ++i) {
            total += this.blockTimes.get(i);
        }
        return total / (double) size;
    }
}

