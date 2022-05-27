package person.rootwhois.blog.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: 陈广生
 * @Date: 2022/01/11/12:44 PM
 * @Description:
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
    @Override
    public Object getCredentials() {
        return token;
    }
}

