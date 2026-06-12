package com.example.common.domain;

import com.example.common.domain.helper.Permissions;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDomain extends AbstractDomain{
    private String title;
    private List<Permissions> permissions;

    public RoleDomain() {
        this.permissions = new ArrayList<>();
    }
}
