package org.design.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;

@Configuration
public class ShiroConfig {

    // 从容器中拿去url配置规则(需在yml配置文件中设置url，并且需要创建ShiroProperties类)
    //@Autowired
    //private ShiroProperties shiroProperties;

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //将自己的验证方式加入容器
    /*@Bean
    public CustomizeRealm myShiroRealm() {
        CustomizeRealm customizeRealm = new CustomizeRealm();
        return customizeRealm;
    }*/

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //以下注释部分，还可以将配置定义到yml中
        Map<String, String> map = new HashMap<>();

        //表示静态资源可以匿名访问
        map.put("/static/**","anon"); //详细规则请见本目录下URL匹配规则.md文件
        map.put("/captcha/**", "anon"); // 允许访问验证码图片生成路径
        //登出
        map.put("/logout", "logout");
        //登录 需要被自定义拦截器拦截获取验证码
        //map.put("/login", "captchaValidate,authc");
        //对所有用户认证
        map.put("/**", "authc");//authc表示需要认证才可以访问,anon表示可以匿名访问

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");

        // 自定义拦截器
        //Map<String, Filter> filterMap = new HashMap<>();
        //filterMap.put("captchaValidate", new CaptchaValidateFilter());
        //filterMap.put("authc", new CustomizeFilter());
        // 设置自定义Filter
        //shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
