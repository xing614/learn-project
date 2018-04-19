package demo.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.assertj.core.util.Maps;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import demo.util.RespCode;
import demo.util.ReturnJsonBody;
import demo.util.TypeConversion;

/**
 * 全局异常管理，springmvc功能，已使用
 * @author liang
 *
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		System.err.println("访问" + request.getRequestURI() + " 发生错误, 错误信息:" + ex.getMessage()+",错误类型： "+ex.toString());
		ModelAndView mv = new ModelAndView();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		mv.setView(view);
		ReturnJsonBody bjb = null;
		Map result = null;
		if (ex instanceof UnauthenticatedException) {
			bjb = new ReturnJsonBody(RespCode.UserOver);
		}else if(ex instanceof UnauthorizedException){
			bjb = new ReturnJsonBody(RespCode.UserAuthority);
		}else if(ex instanceof UnknownAccountException){
			bjb = new ReturnJsonBody(RespCode.UserError);
		}else if(ex instanceof LockedAccountException){
			bjb = new ReturnJsonBody(RespCode.UserLimited);
		}else {  
			bjb = new ReturnJsonBody(RespCode.WARN);
//			mv.addObject("code", "100111");
//			mv.addObject("message", "系统错误");			
		}
		//obj转成map方法
		try {
			result = BeanUtils.describe(bjb);
			mv.addAllObjects(result);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;
	}


//	@Override
//	@ResponseBody
//	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
//			Exception ex) {
//		System.err.println("访问" + request.getRequestURI() + " 发生错误, 错误信息:" + ex.getMessage()+",错误类型："+ex);
//        //ModelAndView mv = new ModelAndView();  
//        response.setContentType("text/html;charset=UTF-8");
//        response.setCharacterEncoding("UTF-8");
//		PrintWriter writer = null;
//		try {
//			writer = response.getWriter();
//		
//        if (ex instanceof UnauthenticatedException) {  
////        	mv.addObject("code", "1000001"); 
////        	mv.addObject("msg", "token错误"); 
//        	writer.write("token错误"); 
//        } else if (ex instanceof UnauthorizedException) {  
////        	mv.addObject("code", "1000002"); 
////        	mv.addObject("msg", "用户无权限");  
//        	writer.write("用户无权限"); 
//        } else if(ex instanceof UnknownAccountException){
////        	mv.addObject("code", "1000010"); 
////        	mv.addObject("msg", "用户/密码错误");     
//        	writer.write("用户或者密码错误"); 
//        } else if(ex instanceof LockedAccountException){
////        	mv.addObject("code", "1000011"); 
////        	mv.addObject("msg", "用户已被锁定");
//        	writer.write("用户已被锁定"); 
//        } else {  
////        	mv.addObject("code", "1001000"); 
////        	mv.addObject("msg", ex.getMessage());
//        }  
//        writer.flush();
//        
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally {
//			writer.close();
//		}
//        return null;  
//    }  
}  
