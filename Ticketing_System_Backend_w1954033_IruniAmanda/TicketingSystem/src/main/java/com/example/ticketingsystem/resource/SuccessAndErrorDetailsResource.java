package com.example.ticketingsystem.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SuccessAndErrorDetailsResource {

    @JsonProperty("messages")
    private String messages;

    @JsonProperty("details")
    private String details;

    @JsonProperty("code")
    private String code;

    @JsonProperty("value")
    private String value;

    @JsonProperty("reportUrl")
    private String reportUrl;


    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public SuccessAndErrorDetailsResource() {
        super();
    }

    public SuccessAndErrorDetailsResource(String messages) {
        super();
        this.messages = messages;
    }

    public SuccessAndErrorDetailsResource(String messages, String value) {
        super();
        this.messages = messages;
        this.value = value;
    }

    public SuccessAndErrorDetailsResource(String messages, String value, String reportUrl) {
        this.messages = messages;
        this.value = value;
        this.reportUrl = reportUrl;
    }
}