package demo.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerExceptionResolver;

import demo.config.MyExceptionHandler;
import demo.config.MySessionManager;
import demo.shiro.filter.ShiroLoginFilter;
import demo.shiro.filter.ShiroPermsFilter;
import demo.shiro.filter.ShiroRolesFilter;

/**
 * shiro配置项
 * @author liang
 *
 */
@Configuration
public class ShiroConfiguration {
	
    @Value("${spring.redis.host}")
    private String host;
    
    @Value("${spring.redis.port}")
    private int port;
    
    @Value("${spring.redis.timeout}")
    private int timeout;
    
	/**
	 * Shiro生命周期处理器，暂时注释，因为有了这个之后，使用@value不管用
	 * 说加入static就可以了,还真可以
	 * 
	 * @return
	 */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
    //指定加密方式方式，也可以在这里加入缓存，当用户超过五次登陆错误就锁定该用户禁止不断尝试登陆
    //由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
//    @Bean(name = "hashedCredentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        credentialsMatcher.setHashAlgorithmName("MD5");//散列算法:这里使用MD5算法;
//        credentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""))
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return credentialsMatcher;
//    }

    //spring允许用户通过depends-on属性指定bean前置依赖的bean,前置依赖的bean会在本bean实例化之前创建好
    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm() {
        ShiroRealm realm = new ShiroRealm();
//        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    //shiro的缓存管理器，需要将缓存管理器注入到安全管理其中 
    @Bean(name = "ehCacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        return ehCacheManager;
    }

    //安全管理器，通过它来链接Realm和用户(文档中称之为Subject.)
    //DefaultWebSecurityManager类主要定义了设置subjectDao，获取会话模式，设置会话模式，设置会话管理器，是否是http会话模式等操作，它继承了DefaultSecurityManager类，实现了WebSecurityManager接口
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        //SecurityManager的Bean中调用setSessionManager(SessionManager sessionManager)方法加载我们的自定义SessionManager
        securityManager.setSessionManager(sessionManager());  
        //改成使用redis的sessionManager
        securityManager.setCacheManager(ehCacheManager());//用户授权/认证信息Cache, 采用EhCache 缓存
        //注入记住我管理器;
//        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * Shiro的Web过滤器,处理拦截资源文件问题
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager  securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
//        LogoutFilter logoutFilter = new LogoutFilter();
//        logoutFilter.setRedirectUrl("/login");
//        filters.put("logout", logoutFilter);
        filters.put("roles", shiroRolesFilter());
        
//        filters.put("shiroLoginFilter", shiroLoginFilter());
        shiroFilterFactoryBean.setFilters(filters);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+host);
        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<>();
        //注意过滤器配置顺序 不能颠倒  
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        //anon     不需要认证；
        //authc     需要认证；authc,roles[user]的意思是好像要认证而且用户必须有user角色。可以是多角色roles["admin,guest"]，如果要改成是or即有其中一个权限即可不是and，需要写AuthorizationFilter子类
        //user     验证通过或RememberMe登录的都可以
        filterChainDefinitionManager.put("/logout", "logout");//注销？
        filterChainDefinitionManager.put("/user/**", "authc,roles[user]");//表示需要认证才可以访问 
        filterChainDefinitionManager.put("/shop/**", "authc,roles[shop]");
        filterChainDefinitionManager.put("/admin/**", "authc,roles[admin]");
        filterChainDefinitionManager.put("/manager/**", "authc,roles[manager]");
        filterChainDefinitionManager.put("/login", "anon");//anon 可以理解为不拦截，表示可以匿名访问
        filterChainDefinitionManager.put("/ajaxLogin", "anon");//anon 可以理解为不拦截
        //filterChainDefinitionManager.put("/**",  "authc,roles[user]");//其他资源全部拦截
        filterChainDefinitionManager.put("/**",  "anon");//其他资源全不拦截
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/ajaxLogin");//登录的url
//        shiroFilterFactoryBean.setLoginUrl("/login");//登录的url
//        shiroFilterFactoryBean.setSuccessUrl("/");//登录成功url
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");//未授权界面

        return shiroFilterFactoryBean;
    }

    /**
     * DefaultAdvisorAutoProxyCreator是用来扫描上下文，寻找所有的Advistor(通知器），
     * 将这些Advisor应用到所有符合切入点的Bean中。所以必须在lifecycleBeanPostProcessor创建之后创建
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    //用于开启Shiro Spring AOP权限注解的支持
    //然后就可以在控制层添加各种注解，如@RequiresRoles("admin")  是在当前用户具有admin角色时可以访问
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        //用户授权/认证信息Cache, 采用EhCache 缓存 
        aasa.setSecurityManager(securityManager);
        return aasa;
    }
    
    /** 
     * 注册全局异常处理 
     * @return 
     */  
    @Bean(name = "exceptionHandler")  
    public HandlerExceptionResolver handlerExceptionResolver() {  
    	MyExceptionHandler myExceptionHandler = new MyExceptionHandler();
        return myExceptionHandler;  
    } 
    
    //自定义sessionManager  将登录成功的认证session存入cookie
    @Bean  
    public SessionManager sessionManager() {  
        MySessionManager mySessionManager = new MySessionManager();  
        return mySessionManager;  
    } 

//    //自定义用于权限不足时返回json数据
//    @Bean(name = "myFormAuthenticationFilter")
//    public MyFormAuthenticationFilter myFormAuthenticationFilter() {
//    	MyFormAuthenticationFilter mf = new MyFormAuthenticationFilter();
//    	return mf;
//    }

    //自定义进入控制层前先判断是否登录
//    @Bean(name="shiroLoginFilter")
//    public ShiroLoginFilter shiroLoginFilter() {  
//    	ShiroLoginFilter shiroLoginFilter = new ShiroLoginFilter();  
//        return shiroLoginFilter;  
//    }     

//  @Bean(name="shiroPermsFilter")
//  public ShiroPermsFilter shiroPermsFilter() {  
//	  ShiroPermsFilter shiroPermsFilter = new ShiroPermsFilter();  
//      return shiroPermsFilter;  
//  } 
  
  @Bean(name="shiroRolesFilter")
  public ShiroRolesFilter shiroRolesFilter() {  
	  ShiroRolesFilter shiroRolesFilter = new ShiroRolesFilter();  
      return shiroRolesFilter;  
  } 
}
