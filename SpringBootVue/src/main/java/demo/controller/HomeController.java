package demo.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//@RequestMapping("/") 放在之下
@RestController
public class HomeController {

    @RequestMapping("/helloworld")  
    @ResponseBody
    public String helloworld() {
        return "helloworld";
    }
    
    @PostMapping("/login")   //post的
    public String login(@RequestParam("username") String name, @RequestParam("password") String pass)
            throws ServletException {
        String token = "";
        if (!"admin".equals(name)) {
            throw new ServletException("找不到该用户");
        }
        if (!"1234".equals(pass)) {
            throw new ServletException("密码错误");
        }
        //设置token，根据user加密，钥只有服务器知道，然后浏览器每次请求都把这个token放在Header里请求，这样服务器只需进行简单的解密就知道是哪个用户了。这样服务器就能专心处理业务，用户多了就加机器。
        //token = Jwts.builder().setSubject(name).claim("roles", "user").setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "base64EncodedSecretKey").compact();
        token = JwtUtil.getToken(name);
        return token;
    }
}