package com.bootcamp.NBFA;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping
    public String hello() {
        return "LALLA";
    }
    
    @GetMapping(path = "/helloagain")
    public String helloAgain() {
        return "Hello again LALLA";
    }
}
