package com.example.service.internal.test;

import com.example.repository.api.TestDao;
import com.example.service.api.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private final TestDao dao;

    public TestServiceImpl(TestDao dao) {
        this.dao = dao;
    }

    @Override
    public String test(String name) {
        return dao.test(name);
    }
}
