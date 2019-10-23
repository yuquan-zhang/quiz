package com.zhang.yong.quit.programminglearning.security.xss;

import com.zhang.yong.quit.programminglearning.utils.StreamUtil;
import org.apache.catalina.util.ParameterMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhang yong
 * @email zhytwo@126.com
 * @date 20190102
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest orgRequest = null;
    private boolean isIncludeRichText = false;

    /**
     * Constructs a request object wrapping the given request.
     * @param request The request to wrap
     * @param isIncludeRichText 是否包含富文本
     * @throws IllegalArgumentException if the request is null
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
        super(request);
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        if(("content".equals(name) || name.endsWith("WithHtml")) && !isIncludeRichText){
            return super.getParameter(name);
        }
        name = HtmlUtils.htmlEscape(name,StandardCharsets.UTF_8.toString());
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = HtmlUtils.htmlEscape(value,StandardCharsets.UTF_8.toString());
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        name = HtmlUtils.htmlEscape(name,StandardCharsets.UTF_8.toString());
        String[] arr = super.getParameterValues(name);
        if(arr != null){
            for (int i=0;i<arr.length;i++) {
                arr[i] = HtmlUtils.htmlEscape(arr[i],StandardCharsets.UTF_8.toString());
            }
        }
        return arr;
    }


    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        name = HtmlUtils.htmlEscape(name,StandardCharsets.UTF_8.toString());
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = HtmlUtils.htmlEscape(value,StandardCharsets.UTF_8.toString());
        }
        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String[]> getParameterMap() {
        ParameterMap paramMap = (ParameterMap) super.getParameterMap();
        for (Iterator iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String[] values = (String[]) entry.getValue();
            for (int i = 0; i < values.length; i++) {
                if (StringUtils.isNotBlank(values[i])) {
                    values[i] = HtmlUtils.htmlEscape(values[i],StandardCharsets.UTF_8.toString());
                }
            }
        }
        return paramMap;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(inputHandlers(super.getInputStream()).getBytes(StandardCharsets.UTF_8));

        return new ServletInputStream() {
            @Override
            public int read() {
                return bais.read();
            }

            public boolean isFinished() {
                return false;
            }

            public boolean isReady() {
                return false;
            }

            public void setReadListener(ReadListener readListener) { }
        };
    }

    private String inputHandlers(ServletInputStream servletInputStream){
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(servletInputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(servletInputStream,reader);
        }
        return cleanXSS(sb.toString());
    }

    private String cleanXSS(String value) {
        value = value.replaceAll("<script>", "&lt;script&gt;")
                .replaceAll("</script>", "&lt;/script&gt;");
//        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
//        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
//        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\"\'][\\s]*javascript:(.*)[\"\']", "\"\"");
        return value;
    }

    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }
}
