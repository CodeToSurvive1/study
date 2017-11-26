package io.github.codetosurvive1.security.shiro.test1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiaojiao.li
 * @version V1.0
 * @title: shiro登录认证
 * @description: 使用ini数据源作为认证
 * @date 2017/11/26 16:53
 */
public class LoginTest {

    @Test
    public void loginTest() {
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro/test1/shiro.ini");

        SecurityManager securityManager = iniSecurityManagerFactory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("lixiaojiao", "123456");
        subject.login(token);
        Assert.assertEquals(true, subject.isAuthenticated());
    }


}
