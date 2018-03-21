package demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import demo.entity.User;

@Mapper
public interface IUserDao {
    int deleteByPrimaryKey(Integer id);
    
    //启动事务用这个标签即可
    @Transactional
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
