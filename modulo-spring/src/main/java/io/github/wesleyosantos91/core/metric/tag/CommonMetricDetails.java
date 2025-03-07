package io.github.wesleyosantos91.core.metric.tag;

import io.micrometer.core.instrument.MeterRegistry;

public abstract class CommonMetricDetails {

    protected String name;
    protected MeterRegistry registry;

    public CommonMetricDetails(String name, MeterRegistry registry) {
        this.name = name;
        this.registry = registry;
    }
}
