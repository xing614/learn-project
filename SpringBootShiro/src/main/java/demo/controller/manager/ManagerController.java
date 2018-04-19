package demo.controller.manager;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.User;
import demo.service.impl.IUserServiceImpl;

@RestController
@RequestMapping(value = "/manager")
public class ManagerController {
    @Autowired
    private IUserServiceImpl userService;
    
    /**
     * 测试权限
     * @return
     */
    //@CrossOrigin 默认允许所有访问源和访问方法。
    @RequestMapping(value = "/getEmail", method = RequestMethod.GET)
    public Map<String,Object> getEmail() {
    	System.out.println("-----------------/manager/getEmail--------------------");
    	User user = (User)SecurityUtils.getSubject().getPrincipal();
    	Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	resultMap.put("email", user.getEmail());
    	System.out.println(user.getEmail());
        return resultMap;
    }
}
