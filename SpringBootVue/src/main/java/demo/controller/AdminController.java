package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.User;
import demo.service.IUserService;
import demo.service.impl.IUserServiceImpl;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private IUserServiceImpl userService;

    @RequestMapping("login")
    @ResponseBody
    public User login(@RequestParam("id")int id) {
        return userService.getUserById(id);
    }
    
}
