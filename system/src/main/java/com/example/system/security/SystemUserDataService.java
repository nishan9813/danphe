package com.example.system.security;

import com.example.service.api.SystemUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Service
public class SystemUserDataService implements UserDetailsService {

    private final SystemUserService userService;

    SystemUserDataService(SystemUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService.findByEmail(email)
                .map(SystemUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }


}

