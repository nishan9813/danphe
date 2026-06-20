package com.example.system.controller.role;

import com.example.common.base.common.Page;
import com.example.common.domain.RoleDomain;
import com.example.common.domain.helper.Permissions;
import com.example.common.exception.NotFoundException;
import com.example.common.filter.RoleFilter;
import com.example.common.modules.SystemModules;
import com.example.service.api.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Tag(name = "02 Roles")
@RestController
@RequestMapping("/api/system/roles")
public class RoleController {

    private final RoleService service;

    RoleController(RoleService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @PostMapping()
    RoleDomain create(RoleDomain req) {
        return service.insert(req);
    }

    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @PutMapping
    RoleDomain update(RoleDomain domain) {
        return service.update(domain);
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) {
        RoleDomain domain = new RoleDomain();
        domain.setId(id);
        service.delete(domain);
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @PostMapping("/search")
    Page<RoleDomain> findAll(@RequestBody RoleFilter filter) {
        return service.findAll(filter);
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @PostMapping("/{id}")
    RoleDomain findById(@PathVariable String id) {
        return service.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found: " + id));
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @PostMapping("/search/all")
    List<RoleDomain> findAllWithoutPagination(@RequestBody(required = false) RoleFilter filter) {
        return service
                .findAllWithoutPagination(filter != null ? filter : new RoleFilter())
                .stream()
                .toList();
    }

    @GetMapping
    List<Permissions> listModules() {
        return Arrays.stream(SystemModules.values())
                .sorted(Comparator.comparingInt(SystemModules::getOrder))
                .map(SystemModules::toPermissions)
                .toList();
    }

}
