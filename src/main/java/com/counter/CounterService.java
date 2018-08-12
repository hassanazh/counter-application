package com.counter;

import com.counter.exception.CounterNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/counter")
@Produces("application/json")
public class CounterService {
    private static ArrayList<Counter> counterArrayList = new ArrayList<>();

    @GET
    @Path("/{countername}")
    @Produces(MediaType.APPLICATION_JSON)
    public Counter getCounterValue(@PathParam(value = "countername") String counterName) {
        Counter counter = getCounter(counterName);

        if(counter != null) {
            return counter;
        }

        throw new CounterNotFoundException("Counter " + counterName + " not found");
    }

    @PUT
    @Path("/{countername}")
    @Produces(MediaType.APPLICATION_JSON)
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
    @Produces(MediaType.APPLICATION_JSON)
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
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Counter> getAllCounters() {
        return counterArrayList;
    }

    /**
     * Used only clear counter list for testing purpose
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteCounters() {
        counterArrayList.clear();
    }

    private Counter getCounter(String counterName) {
        for (Counter singleCounter : counterArrayList) {
            if(singleCounter.getCounterName().equalsIgnoreCase(counterName)) {
                return singleCounter;
            }
        }

        return null;
    }
}
