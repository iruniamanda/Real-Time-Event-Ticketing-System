package com.example.ticketingsystem.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonException extends RuntimeException{

    @JsonIgnore
    private HttpStatus status;

    @JsonProperty(value="message")
    private String message;

    public CommonException (String exception) {
        super(exception);
    }

    public CommonException (String exception, HttpStatus status) {
        super(exception);
        this.message = exception;
        this.status = status;
    }
}