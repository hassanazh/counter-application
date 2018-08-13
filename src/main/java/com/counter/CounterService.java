package com.counter;

import com.counter.exception.CounterNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("/counter")
@Produces(MediaType.APPLICATION_JSON)
public class CounterService {
    private static CopyOnWriteArrayList<Counter> counterArrayList = new CopyOnWriteArrayList<>();

    @GET
    @Path("/{countername}")
    public Counter getCounterValue(@PathParam(value = "countername") String counterName) {
        Counter counter = getCounter(counterName);

        if(counter != null) {
            return counter;
        }

        throw new CounterNotFoundException("Counter " + counterName + " not found");
    }

    @PUT
    @Path("/{countername}")
    public Counter incrementCounterValue(@PathParam("countername") String counterName) {
        Counter counter = getCounter(counterName);

        if(counter != null) {
            counter.incrementCounter();
            return counter;
        }

        return null;
    }

    @POST
    @Path("/{countername}")
    public Counter createCounter(@PathParam("countername") String counterName) {
        Counter counterExists = getCounter(counterName);
        if(counterExists != null) {
            return incrementCounterValue(counterName);
        }

        Counter counter = new Counter(counterName, 1L);
        counterArrayList.add(counter);
        return counter;
    }

    @GET
    public CopyOnWriteArrayList<Counter> getAllCounters() {
        return counterArrayList;
    }

    /**
     * Used only clear counter list for testing purpose
     */
    @DELETE
    public void deleteCounters() {
        counterArrayList.clear();
    }

    private Counter getCounter(final String counterName) {
        return counterArrayList.stream()
            .filter(counter -> counter.getCounterName().equalsIgnoreCase(counterName))
            .findAny()
            .orElse(null);
    }
}
