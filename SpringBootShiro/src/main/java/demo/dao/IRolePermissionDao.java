package demo.dao;

import org.apache.ibatis.annotations.Mapper;

import demo.entity.RolePermission;

@Mapper
public interface IRolePermissionDao {
    int insert(RolePermission record);

    int insertSelective(RolePermission record);
}