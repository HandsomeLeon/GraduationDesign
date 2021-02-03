package org.design.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.design.model.Employee;
import org.design.model.Permission;
import org.design.service.EmployeeService;
import org.design.service.SystemService;

import javax.annotation.Resource;
import java.util.List;

public class CustomizeRealm extends AuthorizingRealm {

    @Resource
    private SystemService systemService;
    @Resource
    private EmployeeService employeeService;

    /**
     * 授权管理
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String) principalCollection.getPrimaryPrincipal();
        List<Permission> permissionList = systemService.findPermissionListByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        for (Permission permission : permissionList) {
            authorizationInfo.addStringPermission(permission.getName());
        }
        return authorizationInfo;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("正在进行验证");
        String username = authenticationToken.getPrincipal().toString();
        Employee employee = employeeService.findByUsername(username);
        if (employee == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(employee, employee.getPassword(), "CustomizeRealm");
        return authenticationInfo;
    }
}
