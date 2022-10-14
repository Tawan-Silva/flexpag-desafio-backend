package com.flexpag.paymentscheduler.rest.controller.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ApiErrorsFields {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant timestamp;
    private Integer status;
    private String error;
    private List<String> message;
    private String path;

    public ApiErrorsFields(
            Instant timestamp,
            Integer status,
            String error,
            List<String> message,
            String path
            ) {

        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
