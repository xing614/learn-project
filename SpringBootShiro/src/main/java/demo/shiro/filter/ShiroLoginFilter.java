package demo.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.util.WebUtils;

import demo.config.MySessionManager;

/*
 * AdviceFilter有点类似SpringMVC中的HandlerInterceptor拦截器，主要用于在访问Controller之前用于判断用户是否登录
 * 
 */
public class ShiroLoginFilter extends AdviceFilter {

	/**
	 * 在访问controller前判断是否登录，返回json，不进行重定向。
	 * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
	 */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String id = WebUtils.toHttp(request).getHeader("Authorization");
        if(id == null)
        	return false;
//        SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute("user");
//        if (null == sysUser && !StringUtils.contains(httpServletRequest.getRequestURI(), "/login")) {
//            String requestedWith = httpServletRequest.getHeader("X-Requested-With");
//            if (StringUtils.isNotEmpty(requestedWith) && StringUtils.equals(requestedWith, "XMLHttpRequest")) {//如果是ajax返回指定数据
//                ResponseHeader responseHeader = new ResponseHeader();
//                responseHeader.setResponse(ResponseHeader.SC_MOVED_TEMPORARILY, null);
//                httpServletResponse.setCharacterEncoding("UTF-8");
//                httpServletResponse.setContentType("application/json");
//                httpServletResponse.getWriter().write(JSONObject.toJSONString(responseHeader));
//                return false;
//            } else {//不是ajax进行重定向处理
//                httpServletResponse.sendRedirect("/login/local");
//                return false;
//            }
//        }
        return true;
    }
}
