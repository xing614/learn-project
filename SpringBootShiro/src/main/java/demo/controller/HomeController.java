package demo.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import demo.security.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//@RequestMapping("/") 放在之下
@RestController
public class HomeController {

	@RequestMapping(value = "/helloworld")  
    @ResponseBody
    public String helloworld() {
        return "helloworld啊啊";
    }
    
}