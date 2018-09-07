package com.counter;

import java.util.concurrent.atomic.AtomicLong;

public class Counter {
    private String counterName;
    private AtomicLong counterValue = new AtomicLong(1L);

    public Counter() { }

    public Counter(String counterName) {
        this.counterName = counterName;
    }

    public String getCounterName() {
        return counterName;
    }

    public AtomicLong getCounterValue() {
        return counterValue;
    }

    public void incrementCounter() {
        counterValue.incrementAndGet();
    }
}
