package org.design.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomizeFilter extends FormAuthenticationFilter {

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        String errorMessage = (String) request.getAttribute("shiroLoginFailure");
        if (errorMessage == null) {
            WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
        } else {
            saveRequestAndRedirectToLogin(request, response);
        }
    }

}
