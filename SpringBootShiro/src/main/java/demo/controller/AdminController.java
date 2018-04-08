package demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.Role;
import demo.entity.User;
import demo.service.IUserService;
import demo.service.impl.IUserServiceImpl;
import demo.util.RespCode;
import demo.util.ReturnJsonBody;
import demo.util.ReturnJsonValue;
/**
 * 用户登陆权限认证管理控制器
 * @author liang
 *
 */
@RestController
//@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private IUserServiceImpl userService;

//    @RequestMapping("select")
//    @ResponseBody
//    public User select(@RequestParam("id")Long id) {
//        return userService.getUserById(id);
//    }
//    
//    @RequestMapping("role")
//    @ResponseBody
//    public Role role(@RequestParam("id")Long id) {
//        return userService.getRoleById(id);
//    }    
//    
//    @RequestMapping("login")
//    @ResponseBody
//    public User login(@RequestParam("username")String username,@RequestParam("password")String password) {
//        return userService.selectByNameAndPass(username,password);
//    }
//    
    //跳转到登录表单页面
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "这是登录/login";
    }
    
    
    @RequestMapping("/")
    public String index(Model model) {
        System.out.println("this is frame");
        return "这是只有/";
    }


    @RequestMapping("/index")
    public String list(Model model) {
        System.out.println("this is index");
        return "这是里/index";
    }
    
    /**
     * 登陆验证，这里方便url测试，正式上线需要使用POST方式提交
     * @param user
     * @return
     */
    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.GET)
    public String ajaxLoginGet(User user) {
    	System.out.println("==========================进入loginget===================");
    	System.out.println(user.getUsername());
        if (user.getUsername() != null && user.getPassword() != null) {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), "login");
            Subject currentUser = SecurityUtils.getSubject();
            try {
            	System.out.println("对用户" + user.getUsername() + "进行登录认证");
                currentUser.login( token );
                //验证是否登录成功
                if (currentUser.isAuthenticated()) {
                    System.out.println("用户[" + user.getUsername() + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
                    return "进入index";
                } else {
                    token.clear();
                    System.out.println("用户[" + user.getUsername() + "]登录认证失败,重新登陆");
                    return "验证失败";
                }
            } catch ( UnknownAccountException uae ) {
                System.out.println("对用户[" + user.getUsername() + "]进行登录验证..验证失败-username wasn't in the system");
            } catch ( IncorrectCredentialsException ice ) {
            	System.out.println("对用户[" + user.getUsername() + "]进行登录验证..验证失败-password didn't match");
            } catch ( LockedAccountException lae ) {
                System.out.println("对用户[" + user.getUsername() + "]进行登录验证..验证失败-account is locked in the system");
            } catch ( AuthenticationException ae ) {
                System.out.println(ae.getMessage());
            }
        }
        return "login";
    }
    
    /**
     * ajax登录post请求
     * 这里有个问题，因为前端使用axios，它的post请求会直接把json放到请求体里，使用data数据，
     * 而最开始后端使用@RequestParam，表示从请求的地址中取出参数，这样是不能提取出请求体中的参数的。
     * 所以后端可以改成使用@RequestBody Map map直接从请求体获取数据
     * 
     * 另外正常请求Content-Type是application/x-www-form-urlencoded;charset=UTF-8，
     * 而axios的post请求是application/json;charset=UTF-8，所以也可以通过前端设置修改
     * axios.post(url,this.transformRequest(param),{
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
                }
            }).then(function(res){
                console.log(res);
            }).catch(function(res){
                console.log(res);
            })
                        方案二：前端不用json传参，改用URLSearchParams
		方案三：前端改写axios的transformRequest
		https://www.jianshu.com/p/042632dec9fb
     * @param map
     * @return
     */
    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
    public Map<String,Object> ajaxLoginPost(@RequestBody Map map) {
    	System.out.println("==========================进入loginpost===================");
    	String username = (String) map.get("username");
    	String password = (String)map.get("password");
    	System.out.println(username);
    	Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if (username != null && password != null) {
          try {
          	UsernamePasswordToken token = new UsernamePasswordToken(username, password);
              SecurityUtils.getSubject().login(token);
              //将token放入响应数据中，之后前端Ajax带着该token
              resultMap.put("token", SecurityUtils.getSubject().getSession().getId());
              resultMap.put("status", 200);
              resultMap.put("message", "登录成功");
  
          } catch (Exception e) {
              resultMap.put("status", 500);
              resultMap.put("message", "message = "+e.getMessage());
          }
        }
        return resultMap;

    }
    
    //跳转到登录表单页面
    @RequestMapping(value = "/getEmail", method = RequestMethod.GET)
    public Map<String,Object> getEmail() {
    	User user = (User)SecurityUtils.getSubject().getPrincipal();
    	Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	resultMap.put("email", user.getEmail());
    	System.out.println(user.getEmail());
        return resultMap;
    }
    

    @RequestMapping("/403")
    public String unauthorizedRole(){
    	System.out.println("------没有权限-------");
        return "403";
    }
}
