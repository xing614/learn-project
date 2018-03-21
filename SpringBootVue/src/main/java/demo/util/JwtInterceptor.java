package demo.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 在spring里很好实现全局拦截,过滤器,拦截器,AOP都可以实现. 
         因为filter是对资源过滤,我们这里没有资源了,只有URL,而AOP着重处理过程.综合考虑,这里选择拦截器比较合适. 
         我们的拦截器:先检查header,取出token,验证
 * @author liang
 *
 */
//public class JwtInterceptor extends HandlerInterceptorAdapter {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new ServletException("invalid Authorization header");
//        }
//        //取得token
//        String token = authHeader.substring(7);
//        try {
//            JwtUtil.checkToken(token);
//            return true;
//        } catch (Exception e) {
//            throw new ServletException(e.getMessage());
//        }
//    }
//}
