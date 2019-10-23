package com.zhang.yong.quit.programminglearning.filters;

import com.alibaba.fastjson.JSONObject;
import com.zhang.yong.quit.programminglearning.views.JsonObject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ShiroSessionFilter extends FormAuthenticationFilter {
    private Logger log = LoggerFactory.getLogger(ShiroSessionFilter.class);
    private final static String X_REQUESTED_WITH_STRING = "X-Requested-With";
    private final static String XML_HTTP_REQUEST_STRING = "XMLHttpRequest";

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }
            if (isAjax((HttpServletRequest)request)) {
                response.getWriter().print(JSONObject.toJSONString(JsonObject.error("当前会话已超时，需要退出重新登陆！")));
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }

            return false;
        }
    }

    public boolean isAjax(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(X_REQUESTED_WITH_STRING);
        if (XML_HTTP_REQUEST_STRING.equalsIgnoreCase(header)) {
            log.debug("当前请求为Ajax请求:{}", httpServletRequest.getRequestURI());
            return Boolean.TRUE;
        }
        log.debug("当前请求非Ajax请求:{}", httpServletRequest.getRequestURI());
        return Boolean.FALSE;
    }
}
