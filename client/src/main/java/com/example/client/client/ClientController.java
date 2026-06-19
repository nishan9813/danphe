package com.example.client.client;

import com.example.common.domain.ClientUserDomain;
import com.example.service.api.ClientUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "01 Client User")
@RestController
@RequestMapping("/api/client/users")
public class ClientController {

    private final ClientUserService service;


    public ClientController(ClientUserService service) {
        this.service = service;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ClientUserDomain create(@RequestBody ClientUserDomain domain) {
        return domain;
    }
}
