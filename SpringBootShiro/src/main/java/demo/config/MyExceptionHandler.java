package demo.config;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常管理
 * @author liang
 *
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
        ModelAndView mv = new ModelAndView();  
        //FastJsonJsonView view = new FastJsonJsonView();  
        //Map<String, Object> attributes = new HashMap<String, Object>();  
        if (ex instanceof UnauthenticatedException) {  
        	mv.addObject("code", "1000001"); 
        	mv.addObject("msg", "token错误"); 
            //attributes.put("code", "1000001");  
            //attributes.put("msg", "token错误");  
        } else if (ex instanceof UnauthorizedException) {  
        	mv.addObject("code", "1000002"); 
        	mv.addObject("msg", "用户无权限");        	
            //attributes.put("code", "1000002");  
            //attributes.put("msg", "用户无权限");  
        } else {  
        	mv.addObject("code", "1000003"); 
        	mv.addObject("msg", ex.getMessage());
        	//attributes.put("code", "1000003");  
            //attributes.put("msg", ex.getMessage());  
        }  
  
        //view.setAttributesMap(attributes);  
        //mv.setView(view);  
        return mv;  
    }  
}  
