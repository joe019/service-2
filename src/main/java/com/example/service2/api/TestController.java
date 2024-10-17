package com.example.service2.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/test")
public class TestController {

    @GetMapping("tracing")
    public String testTracing() {
        log.info("Received request from Service 1");
        return "Hello from Service 2";
    }
}