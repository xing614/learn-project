package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.Role;
import demo.entity.User;
import demo.service.IUserService;
import demo.service.impl.IUserServiceImpl;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private IUserServiceImpl userService;

    @RequestMapping("select")
    @ResponseBody
    public User select(@RequestParam("id")Long id) {
        return userService.getUserById(id);
    }
    
    @RequestMapping("role")
    @ResponseBody
    public Role role(@RequestParam("id")Long id) {
        return userService.getRoleById(id);
    }    
    
    @RequestMapping("login")
    @ResponseBody
    public User login(@RequestParam("username")String username,@RequestParam("password")String password) {
        return userService.selectByNameAndPass(username,password);
    }
    
    
    public User register() {
		return null;
    	
    }
}
