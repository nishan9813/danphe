package com.example.repository.internal;

import com.example.repository.api.TestDao;
import org.springframework.stereotype.Repository;

@Repository
public class TestDaoImpl implements TestDao {

    @Override
    public String test(String name) {
        return "This is from repository: " +name;
    }
}
