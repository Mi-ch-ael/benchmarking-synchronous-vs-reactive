package ru.etu.stud.java.synchronous.tools;

import io.micrometer.core.instrument.Gauge;

public record GaugeGetOneWrapper(Gauge gauge, ThreadObserver threadObserver) {
}
