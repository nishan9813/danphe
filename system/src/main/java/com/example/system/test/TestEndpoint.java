package com.example.system.test;


import com.example.service.api.TestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "01 System User" )
@RestController
@RequestMapping("/api/test")
public class TestEndpoint {

    private final TestService service;

    public TestEndpoint(TestService service) {
        this.service = service;
    }

    @PostMapping
    String test(@RequestBody String name){
        return service.test(name);
    }
}
