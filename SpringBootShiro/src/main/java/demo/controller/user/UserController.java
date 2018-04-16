package demo.controller.user;
//package demo.controller;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import demo.security.JwtUtil;
//
//@RestController
//@RequestMapping(value = "/user")
//public class UserController {
//
//    @PostMapping("/login")   //post的	
//    public String login(@RequestParam("username") String name, @RequestParam("password") String pass,HttpServletResponse response)
//            throws ServletException {
//        String token = "";
//        if (!"admin".equals(name)) {
//            throw new ServletException("找不到该用户");
//        }
//        if (!"1234".equals(pass)) {
//            throw new ServletException("密码错误");
//        }
//        //设置token，根据user加密，钥只有服务器知道，然后浏览器每次请求都把这个token放在Header里请求，这样服务器只需进行简单的解密就知道是哪个用户了。这样服务器就能专心处理业务，用户多了就加机器。
//        //token = Jwts.builder().setSubject(name).claim("roles", "user").setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "base64EncodedSecretKey").compact();
//        token = JwtUtil.getToken(name);
//        response.addHeader("Authorization", "Bearer "+token);
//        return token;
//    }
//    
//    @GetMapping("/success")
//    public String success() {
//        return "恭喜您登录成功";
//    }
//
//    @GetMapping("/getEmail")
//    public String getEmail() {
//        return "xxxx@qq.com";
//    }
//}
