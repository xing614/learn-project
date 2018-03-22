package demo;


import java.util.Date;

import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import demo.filter.JwtFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * 启动类
 * 这个类要放在所有类的同级或上级的包中，不然会产生错误
 * Field userService in demo.controller.AdminController required a bean of type 'demo.service.impl.IUserServiceImpl' that could not be found.
 * Consider defining a bean of type 'demo.service.impl.IUserServiceImpl' in your configuration.
 * 可以不implements CommandLineRunner，这是为了查看数据源信息
 */
@RestController  
//@EnableAutoConfiguration   //自动加载配置信息  
@SpringBootApplication //用了这个其他RestController能读取，该注解指定项目为springboot，由此类当作程序入口，自动装配 web 依赖的环境
public class TestApplication implements CommandLineRunner{  
	
	@Autowired  
    DataSource dataSource; 
	
     @RequestMapping("/home")  
     @ResponseBody
     String home() {  
         return "Hello World222!";  
     }  

     public static void main(String[] args) {  
         SpringApplication.run(TestApplication.class, args);  
     }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("DATASOURCE = " + dataSource);  
	}  
}
