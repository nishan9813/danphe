package com.example.system.security;

import com.example.common.domain.SystemUserDomain;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SystemUserPrincipal implements UserDetails {

    private final SystemUserDomain user;

    public SystemUserPrincipal(SystemUserDomain user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRoleDomain() != null && user.getRoleDomain().getPermissions() != null) {
            user.getRoleDomain().getPermissions().forEach(module ->
                    module.getPermissions().forEach(p ->
                    authorities.add(new GrantedAuthority() {
                        @Override
                        public String getAuthority() {
                            return p.getCode();
                        }
                    })));
        }
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
