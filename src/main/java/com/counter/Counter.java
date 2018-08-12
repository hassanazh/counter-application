package com.counter;

public class Counter {
    private String counterName;
    private Long counterValue;

    public Counter() { }

    public Counter(String counterName, Long counterValue) {
        this.counterName = counterName;
        this.counterValue = counterValue;
    }

    public String getCounterName() {
        return counterName;
    }

    public Long getCounterValue() {
        return counterValue;
    }

    public void incrementCounter() {
        counterValue++;
    }
}
