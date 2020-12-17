package com.banger.bangerapi.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloWorldController {

    @RequestMapping( "/hello")
    @PreAuthorize("hasRole('ROLE_Customer')")
    public String firstPage() {
        return "Hello World";
    }

}
