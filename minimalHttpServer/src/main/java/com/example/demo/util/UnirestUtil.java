package com.example.demo.util;

import kong.unirest.Unirest;

public class UnirestUtil implements AutoCloseable{
    public void send() {
        Unirest.get("http://localhost:9092")
                .header("accept", "application/json")
                .asJson();
    }

    @Override
    public void close() {
        Unirest.shutDown();
    }
}
