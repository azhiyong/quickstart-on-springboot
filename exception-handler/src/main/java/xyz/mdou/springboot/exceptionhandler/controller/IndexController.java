package xyz.mdou.springboot.exceptionhandler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mdou.springboot.exceptionhandler.rest.RestException;

@RestController
public class IndexController {

    @GetMapping
    public String index() {
        throw new RestException("NOT FOUND", "api not found");
    }
}
