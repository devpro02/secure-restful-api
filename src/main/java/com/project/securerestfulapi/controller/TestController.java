package com.project.securerestfulapi.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello")
    public String index() {
        return "Hello World!";
    }
    @GetMapping("/hi")
    public String index2() {
        return "Hi World!";
    }
}
