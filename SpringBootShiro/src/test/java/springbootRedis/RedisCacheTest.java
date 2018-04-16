package springbootRedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.TestApplication;
import demo.ControllerRedisCache.RedisCache;
import demo.entity.User;
import demo.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class RedisCacheTest {
    
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    RedisCache redisCache;
    

    @Test
    public void redisTest() throws Exception {

        //保存字符串
        stringRedisTemplate.opsForValue().set("bbb", "333");
        //读取字符串
        String aaa = stringRedisTemplate.opsForValue().get("aaa");
        System.out.println("xaisixiasixisaixi+"+aaa);
    }
    
   @Test
    public void CacheTest() {
	    System.out.println("=============");//可以看到第一次查询会有SQL语句，第二次就没了
        User User = redisCache.getUser(2);
        System.out.println("第一次查询结果：");
        System.out.println(User.toString());

        User User1 = redisCache.getUser(2);
        System.out.println("第二次查询结果：");
        System.out.println(User1.toString());
        
        redisCache.updateByPrimaryKeySelective(2);
        
        User User2 = redisCache.getUser(2);
        System.out.println("第三次查询结果：");
        System.out.println(User2.toString());

    }

}

