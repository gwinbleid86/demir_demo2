package com.example.demo.controller;

import com.example.demo.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @PostMapping
    public HttpStatus index(@RequestBody String request) {
        new Thread(() -> mainService.createFile(request)).start();
        return HttpStatus.OK;
    }
}
