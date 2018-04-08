package demo.dao;

import org.apache.ibatis.annotations.Mapper;

import demo.entity.Role;

@Mapper
public interface IRoleDao {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}