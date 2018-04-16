package demo.dao;

import org.apache.ibatis.annotations.Mapper;

import demo.entity.UserRole;

@Mapper
public interface IUserRoleDao {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}