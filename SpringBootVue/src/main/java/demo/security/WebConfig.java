package demo.security;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @author liang
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * CORSConfiguration配置,跨域访问
	 */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //addMapping("/**")表示允许全部请求跨域,
    	//allowedMethods("*")允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等。
    	//allowedOrigins：允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如："http://www.baidu.com"，只有百度可以访问我们的跨域资源。
        registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
    }
    

    //为拦截器添加到SpringBoot的配置中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//InterceptorRegistry内的addInterceptor需要一个实现HandlerInterceptor接口的拦截器实例，addPathPatterns方法用于设置拦截器的过滤路径规则
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
    	//registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/user/*");
        registry.addInterceptor(new JwtInterceptor()).excludePathPatterns("/user/login");
    }
    
    //将格式改为utf-8
    @Bean
    public HttpMessageConverter<String> responseBodyConverter(){
    	StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    	return converter;
    }
    //Spring Boot底层通过HttpMessageConverters依靠Jackson库将Java实体类输出为JSON格式。
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	super.configureMessageConverters(converters);
    	converters.add(responseBodyConverter());
    }
}