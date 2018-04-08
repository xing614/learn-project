package demo.dao;

import demo.entity.UserRole;

public interface IUserRoleDao {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}