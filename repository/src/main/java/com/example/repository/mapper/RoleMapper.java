package com.example.repository.mapper;

import com.example.common.entity.RoleEntity;
import com.example.common.filter.RoleFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<RoleEntity> findAll(@Param("filter") RoleFilter filter);

    int countAll(@Param("filter") RoleFilter filter);

    RoleEntity findById(@Param("id") String id);

    RoleEntity findByEmail(@Param("email") String email);

    int insert(RoleEntity entity);

    int update(RoleEntity entity);

    int delete(@Param("entity") RoleEntity Entity);
}
