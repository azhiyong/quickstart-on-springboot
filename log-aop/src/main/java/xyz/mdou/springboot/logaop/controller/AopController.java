package xyz.mdou.springboot.logaop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aop")
public class AopController {

    @GetMapping
    public String aop(String abc) {
        return "aop log " + abc;
    }
}
