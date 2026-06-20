package com.example.repository.mapper;

import com.example.common.entity.SystemUserEntity;
import com.example.common.filter.SystemUserFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemUserMapper {

    int insert(SystemUserEntity entity);

    SystemUserEntity findByEmail(String email);

    SystemUserEntity findById(String id);

    List<SystemUserEntity> findAll(@Param("filter") SystemUserFilter filter);

    int countAll(@Param("filter") SystemUserFilter filter);

    int update(SystemUserEntity entity);

    int delete(@Param("domain")SystemUserEntity entity);
}
