package com.example.system.controller.user;

import com.example.common.domain.SystemUserDomain;
import com.example.common.response.SystemUserResponse;
import com.example.service.api.SystemUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system/users")
public class SystemUserController {

    private final SystemUserService service;

    public SystemUserController(SystemUserService service) {
        this.service = service;
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    SystemUserResponse create(@RequestBody SystemUserDomain domain) {
        return SystemUserResponse.from(service.create(domain));
    }
}
