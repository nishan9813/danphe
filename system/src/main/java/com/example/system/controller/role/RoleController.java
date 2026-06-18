package com.example.system.controller.role;

import com.example.common.domain.RoleDomain;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "02 Roles")
@RestController
@RequestMapping("/api/system/roles")
public class RoleController {

    public RoleDomain create(RoleDomain req) {
        return req;
    }

}
