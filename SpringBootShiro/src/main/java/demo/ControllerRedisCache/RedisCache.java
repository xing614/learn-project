package demo.ControllerRedisCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import demo.entity.User;
import demo.service.IUserService;

/**
 * 获取数据，并且做缓存处理
 * @author z77z
 *当注解作用在类上时，表明这些类是交给spring容器进行管理的，而当使用@Autowired和@Resource时，表明我需要某个属性、方法或字段，但是并不需要我自己去new一个，只需要使用注解， spring容器会自动的将我需要的属性、方法或对象创造出来
 */
@Component
public class RedisCache {
    
    @Autowired
    IUserService iUserService;
    
    @Cacheable(value="user") //如果注释就是不用缓存处理,作用是主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
    public User getUser(long id) {
    	User user = iUserService.getUserById(id);
        return user;
    }
    
    //修改
    //@CachePut(value="user") //作用是主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用
    @CacheEvict(value="user",allEntries=true)
    public void updateByPrimaryKeySelective(long id) {
    	User user = iUserService.getUserById(id);
    	user.setEmail("admin@163.com");
    	iUserService.updateByPrimaryKeySelective(user);
    }
}

