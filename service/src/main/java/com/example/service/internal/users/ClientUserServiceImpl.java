package com.example.service.internal.users;

import com.example.common.base.common.Page;
import com.example.common.domain.ClientUserDomain;
import com.example.common.filter.ClientUserFilter;
import com.example.service.api.ClientUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientUserServiceImpl implements ClientUserService {
    @Override
    @Transactional
    public ClientUserDomain insert(ClientUserDomain req) {
        return null;
    }

    @Override
    public ClientUserDomain update(ClientUserDomain req) {
        return null;
    }

    @Override
    public String delete(ClientUserDomain req) {
        return "";
    }

    @Override
    public Optional<ClientUserDomain> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<ClientUserDomain> findOne(ClientUserFilter filter) {
        return Optional.empty();
    }

    @Override
    public List<ClientUserDomain> findAllWithoutPagination(ClientUserFilter filter) {
        return List.of();
    }

    @Override
    public Page<ClientUserDomain> findAll(ClientUserFilter filter) {
        return null;
    }

    @Override
    public ClientUserDomain create(ClientUserDomain domain) {
        return null;
    }

    @Override
    public Optional<ClientUserDomain> findByEmail(String email) {
        return Optional.empty();
    }
}
