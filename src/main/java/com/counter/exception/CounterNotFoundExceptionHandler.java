package com.counter.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CounterNotFoundExceptionHandler implements ExceptionMapper<CounterNotFoundException> {
    public class ErrorMessage {
        public String status;
        public String errorMessage;

        public ErrorMessage(String status, String errorMessage) {
            this.errorMessage = errorMessage;
            this.status = status;
        }
    }

    public Response toResponse(CounterNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity(new ErrorMessage(Response.Status.NOT_FOUND.toString(), ex.getMessage()))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}