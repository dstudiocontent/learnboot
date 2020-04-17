package com.extack.learnboot.controller;

import com.extack.learnboot.model.Welcome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    private static final String template = "Welcome, %s";

    @GetMapping("/welcome")
    public Welcome greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        if(name.contains("a"))
            return new Welcome(String.format(template, name));
        else return null;
    }
}
