package com.example.client.security;

import com.example.common.domain.ClientUserDomain;
import com.example.common.permissions.ClientUserRole;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class ClientUserPrincipal implements UserDetails {

    @Getter
    private final ClientUserDomain user;

    @Getter
    private String activeOrgId;

    @Getter
    private String activeMemberId;

    private List<GrantedAuthority> authorities;

    public ClientUserPrincipal(ClientUserDomain user) {
        this.user = user;
        this.authorities = buildPersonalAuthorities(user.getRole());
    }

//    public void activateOrgContext(String orgId, String memberId) {
//        this.activeOrgId = orgId;
//        this.activeMemberId = memberId;
//
//        List<GrantedAuthority> all = new ArrayList<>(buildPersonalAuthorities(user.getRole()));
//        if (role.getPermissions() != null) {
//            for (Permissions module : role.getPermissions()) {
//                if (module.getPermissions() != null) {
//                    for (var perm : module.getPermissions()) {
//                        all.add(new SimpleGrantedAuthority(perm.getCode()));
//                    }
//                }
//            }
//        }
//        this.authorities = all;
//    }

//    public boolean hasOrgContext() {
//        return activeOrgId != null;
//    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isEnabled() {
        return user.isVerified();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    private static List<GrantedAuthority> buildPersonalAuthorities(ClientUserRole role) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        role.getPermissions().forEach(p -> list.add(new SimpleGrantedAuthority(p.name())));
        return list;
    }
}
