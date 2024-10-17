package com.example.service2.activities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestActivityImpl implements TestActivity {
    @Override
    public void printHello() {
        log.info("Hello from test activity!");
    }
}
