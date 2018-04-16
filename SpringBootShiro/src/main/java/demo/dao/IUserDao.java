package demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import demo.entity.User;

@Mapper
public interface IUserDao {
    int deleteByPrimaryKey(Long id);
    
    //启动事务用这个标签即可
    @Transactional
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByNameAndPass(@Param("username")String username,@Param("password")String password);
    
    User selectByName(@Param("username")String userName);
    
    User selectByEmailAndPawd(@Param("email")String email,@Param("password")String password);
    
}
