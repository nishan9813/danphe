package com.example.system.controller.SystemUser;

import com.example.common.base.common.Page;
import com.example.common.domain.SystemUserDomain;
import com.example.common.filter.SystemUserFilter;
import com.example.common.response.SystemUserResponse;
import com.example.service.api.SystemUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "01 System Users")
@RestController
@RequestMapping("/api/system/users")
public class SystemUserController {

    private final SystemUserService service;

    public SystemUserController(SystemUserService service) {
        this.service = service;
    }


    @PreAuthorize("hasAuthority('SYSTEM_USER_CREATE')")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    SystemUserResponse create(@RequestBody SystemUserDomain domain) {
        return SystemUserResponse.from(service.create(domain));
    }


    @PreAuthorize("hasAuthority('SYSTEM_USER_READ')")
    @PostMapping("/all")
    Page<SystemUserResponse> findAll(@RequestBody SystemUserFilter filter) {
        return service.findAll(filter).map(SystemUserResponse::from);
    }
}
