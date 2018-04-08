package demo.dao;

import demo.entity.RolePermission;

public interface IRolePermissionDao {
    int insert(RolePermission record);

    int insertSelective(RolePermission record);
}