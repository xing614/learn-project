package demo.config.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import demo.dao.IPermissionDao;
import demo.dao.IRoleDao;
import demo.dao.IUserDao;
import demo.entity.Permission;
import demo.entity.Role;
import demo.entity.User;

/**
 * 获取用户的角色和权限信息
 * 自定义权限匹配和账号密码匹配 
 * @author liang
 *
 */
public class ShiroRealm extends AuthorizingRealm {
	
    @Autowired
    private IUserDao UserDao;
    @Autowired
    private IRoleDao RoleDao;
    @Autowired
    private IPermissionDao PermissionDao;
    

    /**
     * 权限认证
     * 根据用户的权限信息做授权判断，这一步是以doGetAuthenticationInfo为基础的，
     * 只有在有用户信息后才能根据用户的角色和授权信息做判断是否给用户授权，因此这里的Roles和Permissions是用户的两个重点判断依据
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("权限认证方法：doGetAuthorizationInfo");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
//      String loginName = (String) super.getAvailablePrincipal(principalCollection);
      User user = (User) principalCollection.getPrimaryPrincipal();
//      //到数据库查是否有此对象
//      User user = null;// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//      user = userMapper.findByName(loginName);
      if (user != null) {
          //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
          SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
          //用户的角色集合
          info.addRoles(user.getRoleStrlist()); 
          //用户的权限集合
          info.addStringPermissions(user.getPerminsStrlist()); 

          return info;
      }
      // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
      return null;
	}

    /**
     * 登录(身份)认证,也就是说验证用户输入的账号和密码是否正确。
     * 获取用户的权限信息，这是为下一步的授权做判断，获取当前用户的角色和这些角色所拥有的权限信息
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("身份认证方法：MyShiroRealm.doGetAuthenticationInfo()");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        //查出是否有此用户
        User hasUser = UserDao.selectByNameAndPass(username, password);
        if(hasUser == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if(hasUser.getStatus()!=1) {
        	throw new LockedAccountException(); //帐号锁定
        }
        if (hasUser != null) {
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            List<Role> rlist = RoleDao.findRoleByUid(hasUser.getId());//获取用户角色,根据用户id查询角色id，name
            List<Permission> plist = PermissionDao.findPermissionByUid(hasUser.getId());//获取用户权限,根据用户角色表的用户id查询权限表的全部数据
            List<String> roleStrlist=new ArrayList<String>();////用户的角色集合
            List<String> perminsStrlist=new ArrayList<String>();//用户的权限集合
            for (Role role : rlist) {
                roleStrlist.add(role.getName());
            }
            for (Permission uPermission : plist) {
                perminsStrlist.add(uPermission.getName());
            }
            hasUser.setRoleStrlist(roleStrlist);
            hasUser.setPerminsStrlist(perminsStrlist);
//            Session session = SecurityUtils.getSubject().getSession();
//            session.setAttribute("user", hasUser);//成功则放入session
         // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(hasUser, password, getName());//这是返回的身份凭据？
        }
        return null;
	}

}
