package demo.shiro.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import demo.util.JsonUtils;
import demo.util.RespCode;
import demo.util.ReturnJsonBody;

/**
 * 扩展RolesAuthorizationFilter，用于ajax访问接口登录但是角色认证不通过的处理
 * @author liang
 *
 */
public class ShiroRolesFilter extends RolesAuthorizationFilter {
	
	/**
	 * 该方法用于设置角色验证失败后的操作，最初是：认证失败处理,逻辑就是如果登录实体为null就保存请求和跳转登录页面,否则就跳转无权限配置页面
	 * 现在修改成传输json数据
	 */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        System.out.println("----------------------------角色过滤-----------------------------");
    	HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;  
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;  
  
        Subject subject = getSubject(servletRequest, servletResponse);  
  
        if (subject.getPrincipal() == null) {  
            if (demo.util.WebUtils.isAjax(httpRequest)) {  
            	try {
					demo.util.WebUtils.sendJson(httpResponse, JsonUtils.objectToJsonStr(new ReturnJsonBody(RespCode.UserOver)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            } else {  
                saveRequestAndRedirectToLogin(servletRequest, servletResponse);  
            }  
            System.out.println("用户为空");
        } else {  
            if (demo.util.WebUtils.isAjax(httpRequest)) {  
            	try {
					demo.util.WebUtils.sendJson(httpResponse, JsonUtils.objectToJsonStr(new ReturnJsonBody(RespCode.UserAuthority)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            } else {  
                String unauthorizedUrl = getUnauthorizedUrl();  
                if (StringUtils.hasText(unauthorizedUrl)) {  
                    WebUtils.issueRedirect(servletRequest, servletResponse, unauthorizedUrl);  
                } else {  
                    WebUtils.toHttp(servletResponse).sendError(401);  
                }  
            }
            System.out.println("用户不为空");
        }  
        return false;  
    }
    
    /**
     * 重写
     * 判断是否拥有角色权限
     * 最初判断是需要有所有角色权限才可以，现在改成只要有一个就可以
     */
    @SuppressWarnings({"unchecked"})
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  
            throws IOException {  
  
        Subject subject = getSubject(request, response);  
        String[] rolesArray = (String[]) mappedValue;  
  
        if (rolesArray == null || rolesArray.length == 0) {  
            // no roles specified, so nothing to check - allow access.  
            return true;  
        }  
  
        Set<String> roles = CollectionUtils.asSet(rolesArray);  
        for (String role : roles) {  
            if (subject.hasRole(role)) {  
                return true;  
            }  
        }  
        return false;  
    }  
}
