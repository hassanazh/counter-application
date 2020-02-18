package com.counter;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class CounterServiceTest {
    private static final String SERVICE_URL
            = "http://localhost:8080/counter";
    public static final String COUNTER_VALUE = "counterValue";
    public static final String COUNTER_NAME = "counterName";
    private static ArrayList<String> counterNames = new ArrayList<>();

    @BeforeClass
    public static void initializeCounter()  {
        counterNames.add("counter1");
        counterNames.add("counter2");
        counterNames.add("counter3");

        counterNames.forEach(counterName -> {
            HttpUriRequest request = new HttpPost(SERVICE_URL + "/" + counterName);
            HttpResponse httpResponse = null;
            try {
                httpResponse = HttpClientBuilder.create().build().execute(request);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
            assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        });
    }

    @Test
    public void getCounter() throws IOException {
        HttpUriRequest request = new HttpGet(SERVICE_URL + "/counter1");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String jsonString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = new JSONObject(jsonString);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        assertEquals(jsonObject.get(COUNTER_VALUE), 1);
    }

    @Test
    public void getCounterWhenCounterDoesntExist() throws IOException {
        HttpUriRequest request = new HttpGet(SERVICE_URL + "/counter4");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String jsonString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = new JSONObject(jsonString);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        assertEquals(jsonObject.get("status"), "Not Found");
    }

    @Test
    public void incrementCounter() throws IOException {
        HttpUriRequest request = new HttpPut(SERVICE_URL + "/counter2");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String jsonString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = new JSONObject(jsonString);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        assertEquals(jsonObject.get(COUNTER_VALUE), 2);
    }

    @Test
    public void getAllCounters() throws IOException {
        HttpUriRequest request = new HttpGet(SERVICE_URL);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String jsonString = EntityUtils.toString(httpResponse.getEntity());
        JSONArray jsonArray = new JSONArray(jsonString);


        assertEquals(jsonArray.getJSONObject(0).get(COUNTER_NAME), "counter1");
        assertEquals(jsonArray.getJSONObject(0).get(COUNTER_VALUE), 1);
        assertEquals(jsonArray.getJSONObject(1).get(COUNTER_NAME), "counter2");
        assertEquals(jsonArray.getJSONObject(1).get(COUNTER_VALUE), 2);
        assertEquals(jsonArray.getJSONObject(2).get(COUNTER_NAME), "counter3");
        assertEquals(jsonArray.getJSONObject(2).get(COUNTER_VALUE), 1);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @AfterClass
    public static void destroyList() throws IOException {
        HttpClientBuilder.create().build().execute(new HttpDelete(SERVICE_URL));
        counterNames.clear();
    }
}
