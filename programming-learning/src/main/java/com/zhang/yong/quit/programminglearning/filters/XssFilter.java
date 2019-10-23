package com.zhang.yong.quit.programminglearning.filters;

import com.zhang.yong.quit.programminglearning.security.xss.XssHttpServletRequestWrapper;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhang yong
 * @email zhytwo@126.com
 * @date 20190102
 */
public class XssFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(XssFilter.class);

    private static boolean IS_INCLUDE_RICH_TEXT = false;//是否过滤富文本内容

    public List<String> excludes = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        if(logger.isDebugEnabled()){
            logger.debug("xss filter init~~~~~~~~~~~~");
        }
        String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
        if(StringUtils.isNotBlank(isIncludeRichText)){
            IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean(isIncludeRichText);
        }
        String temp = filterConfig.getInitParameter("excludes");
        if (StringUtils.isNotBlank(temp)) {
            excludes.addAll(Arrays.asList(temp.split(",")));
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if(handleExcludeURL(req)){
            filterChain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(req,IS_INCLUDE_RICH_TEXT);
        filterChain.doFilter(xssRequest, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request) {
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        for (String exclude : excludes) {
            Pattern p = Pattern.compile("^" + exclude);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }



    @Override
    public void destroy() {}

}
