package com.example.demo.util;

import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UnirestUtil{

    @Value("${unirest.second_app.url}")
    private String url;

    public void send() {
        Unirest.get(url).asJson();
        close();
    }

    private void close() {
        Unirest.shutDown();
    }
}
