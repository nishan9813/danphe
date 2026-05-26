package com.example.repository.mapper;

import com.example.common.entity.SystemUserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemUserMapper {
    int insert(SystemUserEntity entity);
}
