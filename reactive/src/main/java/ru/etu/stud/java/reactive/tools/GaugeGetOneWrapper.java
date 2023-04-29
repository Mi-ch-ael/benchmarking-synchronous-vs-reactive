package ru.etu.stud.java.reactive.tools;

import io.micrometer.core.instrument.Gauge;

public record GaugeGetOneWrapper(Gauge gauge, ThreadObserver threadObserver) {
}
