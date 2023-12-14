package com.example.second_project.controller;

import com.example.second_project.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping
    public HttpStatus index() {
        mainService.run();
        return HttpStatus.OK;
    }
}
