package com.counter.exception;

public class CounterNotFoundException extends RuntimeException {
    public CounterNotFoundException(String exceptionMsg)
    {
        super(exceptionMsg);
    }
}
