package person.rootwhois.blog.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.service.AdminService;
import person.rootwhois.blog.util.JwtUtils;

/**
 * @Author: 陈广生
 * @Date: 2022/01/11/12:32 PM
 * @Description:
 */

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Lazy
    @Autowired
    AdminService adminService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        JwtToken jwtToken = (JwtToken) token;
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        Admin admin = adminService.getById(Long.parseLong(userId));
        if (admin == null) {
            throw new UnknownAccountException("账户不存在");
        }

        return new SimpleAuthenticationInfo(admin, jwtToken.getCredentials(), getName());
    }
}
