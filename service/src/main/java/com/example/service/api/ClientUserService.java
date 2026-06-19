package com.example.service.api;

import com.example.common.domain.ClientUserDomain;
import com.example.common.filter.ClientUserFilter;

import java.util.Optional;

public interface ClientUserService extends Service<ClientUserDomain, ClientUserFilter>{

    ClientUserDomain create(ClientUserDomain domain);

    Optional<ClientUserDomain> findByEmail(String email);
}
