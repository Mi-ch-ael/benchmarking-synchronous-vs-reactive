package ru.etu.stud.java.synchronous.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;

@Component
@Scope("prototype")
public class CpuTimeHolder {
    private final ThreadMXBean threadBean;
    private final Logger logger = LoggerFactory.getLogger(CpuTimeHolder.class);
    private final Map<Long, Long> startCpuTimes = Collections.synchronizedMap(new HashMap<>());
    private final List<Long> delaysMs = Collections.synchronizedList(new ArrayList<>());
    public CpuTimeHolder() {
        threadBean = ManagementFactory.getThreadMXBean();
        if(!threadBean.isThreadCpuTimeSupported()) {
            logger.error("Thread CPU time measurement not supported");
            throw new IllegalStateException("Thread CPU time measurement not supported");
        }
        if(!threadBean.isThreadCpuTimeEnabled()) {
            logger.info("Thread CPU time measurement disabled by default. Enabling now.");
            threadBean.setThreadContentionMonitoringEnabled(true);
        }
    }
    public void startMeasure(Thread thread) {
        long threadId = thread.getId();
        startCpuTimes.put(threadId, threadBean.getThreadCpuTime(threadId));
    }
    public void endMeasure(Thread thread) {
        long threadId = thread.getId();
        delaysMs.add((threadBean.getThreadCpuTime(threadId) - startCpuTimes.get(threadId)) / 1000000);
    }
    public long last() {
        if(delaysMs.size() == 0) return -1;
        return delaysMs.get(delaysMs.size() - 1);
    }
}
