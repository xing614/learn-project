package demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import demo.entity.Permission;

@Mapper
public interface IPermissionDao {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
    
    List<Permission> findPermissionByUid(Long id);
}