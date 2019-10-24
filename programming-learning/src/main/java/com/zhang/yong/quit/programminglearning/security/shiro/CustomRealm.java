package com.zhang.yong.quit.programminglearning.security.shiro;

import com.zhang.yong.quit.programminglearning.modules.admin.bean.UserBean;
import com.zhang.yong.quit.programminglearning.utils.SecurityUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zhang yong
 * @email zhytwo@126.com
 * @date 20190102
 */
public class CustomRealm extends AuthorizingRealm {

    @Value("${my.app.admin.username}")
    private String admin;
//
//    @Autowired
//    private RemoteService remoteService;
//
//    @Autowired
//    private UserService userService;


    //告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
    {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        hashMatcher.setStoredCredentialsHexEncoded(false);
        hashMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(hashMatcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        UserBean userBean = (UserBean) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        System.out.println("获取角色信息："+user.getRoles());
//        System.out.println("获取权限信息："+user.getPermissions());
//        info.setRoles(user.getRoles());
//        info.setStringPermissions(user.getPermissions());
//        if(admin.equals(user.getMobile())) {
//            info.setRoles(Collections.singleton("*"));
//            info.setStringPermissions(Collections.singleton("*"));
//        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;
        String username = uToken.getUsername();
        String password = new String(uToken.getPassword());
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        // 暂时用远程登录逻辑替代本地登录逻辑，后期做用户中心的时候，需要改为本地登录。
        UserBean userBean = new UserBean(); //remoteService.login(username,password);
//        userService.setRolesAndMenusForUser(user);
        return new SimpleAuthenticationInfo(userBean, SecurityUtil.getPassword(password,null), getName());
    }
}
