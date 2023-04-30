package ru.etu.stud.java.synchronous.wrappers;

import io.micrometer.core.instrument.Gauge;
import ru.etu.stud.java.synchronous.tools.CpuTimeHolder;

public record GaugeGetOneWrapper(CpuTimeHolder cpuTimeHolder, Gauge gauge) {
}
