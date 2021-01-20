package com.holybell.mockmq.v2;

import java.util.Map;

public class HMessage<T> {

    private Map<String, Object> headers;
    private T body;

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public HMessage() {

    }

    public HMessage(Map<String, Object> headers, T body) {
        this.headers = headers;
        this.body = body;
    }
}
