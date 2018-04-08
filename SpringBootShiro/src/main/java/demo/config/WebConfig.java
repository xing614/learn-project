//package demo.config;
//
//import java.nio.charset.Charset;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
////import demo.security.JwtInterceptor;
//
///**
// * 
// * @author liang
// *
// */
//@Configuration
//@EnableWebMvc
//@ComponentScan
//public class WebConfig extends WebMvcConfigurerAdapter {
//
//    //Spring Boot底层通过HttpMessageConverters依靠Jackson库将Java实体类输出为JSON格式。
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//    	super.configureMessageConverters(converters);
//    	converters.add(responseBodyConverter());
//    	//正常在ResponseBodyAdvice 中处理返回值为Object类型没问题，但是如果处理string类型就有可能出问题，有可能遇到类型不匹配的问题
//    	//HttpMessageConverter是根据Controller的原始返回值类型进行处理的，而我们在ResponseAdvisor中改变了返回值的类型。
//    	//最容易出现问题的就是String类型，因为在所有的HttpMessageConverter实例集合中，StringHttpMessageConverter要比其它的Converter排得靠前一些。
//    	//尝试将处理Object类型的HttpMessageConverter放得靠前一些，这可以在Configuration类中完成：
//    	converters.add(0,mappingJackson2HttpMessageConverter());
//    }
//    
//	/**
//	 * CORSConfiguration配置,跨域访问
//	 */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //addMapping("/**")表示允许全部请求跨域,
//    	//allowedMethods("*")允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等。
//    	//allowedOrigins：允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如："http://www.baidu.com"，只有百度可以访问我们的跨域资源。
//        registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
//    }
//    
//
//    //为拦截器添加到SpringBoot的配置中
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//    	//InterceptorRegistry内的addInterceptor需要一个实现HandlerInterceptor接口的拦截器实例，addPathPatterns方法用于设置拦截器的过滤路径规则
//        // 多个拦截器组成一个拦截器链
//        // addPathPatterns 用于添加拦截规则
//        // excludePathPatterns 用户排除拦截
//    	//registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/user/*").excludePathPatterns("/user/login");
//    }
//    
//    //用来对参数值和返回值的转换处理，将格式改为utf-8
//    @Bean
//    public HttpMessageConverter<String> responseBodyConverter(){
//    	StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//    	return converter;
//    }
//    
//    //没写这个之前，控制层直接返回类对象会报错。作用是用于将对象转换为 JSON。使用 Jackson 的 ObjectMapper 读取/编写 JSON 数据。它转换媒体类型为 application/json 的数据。
//    @Bean  
//    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){ 
//    	MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();  
//        //全局修改，设置日期格式 。默认日期格式是把2017-10-25  转换为 时间戳：15003323990
//    	//如果想要单个bean的某个日期字段显示年月日时分秒的话，只需要在对应日期的get方法上添加@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")即可
//        ObjectMapper objectMapper = new ObjectMapper();  
//        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd");  
//        objectMapper.setDateFormat(smt);  
//        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);  
//        //设置中文编码格式  
//        List<MediaType> list = new ArrayList<MediaType>();  
//        list.add(MediaType.APPLICATION_JSON_UTF8);  
//        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);  
//        return mappingJackson2HttpMessageConverter;  
//        //return new MappingJackson2HttpMessageConverter();  
//    } 
//    
//
//}