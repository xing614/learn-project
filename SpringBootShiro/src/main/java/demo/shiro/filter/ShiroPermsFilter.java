package demo.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

/**
 * 权限不足的过滤
 * @author liang
 *
 */
public class ShiroPermsFilter extends PermissionsAuthorizationFilter {
	/**
	 * shiro认证perms资源失败后回调方法
	 */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {

    	System.out.println("-------------------权限过滤------------------");
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//        String requestedWith = httpServletRequest.getHeader("X-Requested-With");
//        if (StringUtils.isNotEmpty(requestedWith) &&
//                StringUtils.equals(requestedWith, "XMLHttpRequest")) {//如果是ajax返回指定格式数据
//            ResponseHeader responseHeader = new ResponseHeader();
//            responseHeader.setResponse(ResponseHeader.SC_FORBIDDEN, null);
//            httpServletResponse.setCharacterEncoding("UTF-8");
//            httpServletResponse.setContentType("application/json");
//            httpServletResponse.getWriter().write(JSONObject.toJSONString(responseHeader));
//        } else {//如果是普通请求进行重定向
//            httpServletResponse.sendRedirect("/403");
//        }
        return false;
    }
}
