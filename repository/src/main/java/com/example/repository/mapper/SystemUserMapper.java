package com.example.repository.mapper;

import com.example.common.entity.SystemUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SystemUserMapper {
    int insert(SystemUserEntity entity);
    SystemUserEntity findByEmail(String email);
    SystemUserEntity findById(String id);
}
